package com.live.pollprojectrestapi.infra.spi;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.Password;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.live.pollprojectrestapi.domain.port.provider.KeycloakProviderPort;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.UUID;

@Configuration
@Getter
public class KeycloakProvider implements KeycloakProviderPort {

    @Value("${keycloak.auth-server-url}")
    public String serverURL;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${keycloak.resource}")
    public String clientID;

    @Value("${keycloak.credentials.secret}")
    public String clientSecret;

    @Value("${keycloak.admin.username}")
    public String adminUser;

    @Value("${keycloak.admin.password}")
    public String adminPassword;

    @Override
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverURL)
                .clientId(clientID)
                .clientSecret(clientSecret)
                .username(adminUser)
                .password(adminPassword)
                .build();
    }

    @Override
    public KeycloakBuilder getInstanceWithPassword(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverURL)
                .clientId(clientID)
                .clientSecret(clientSecret)
                .username(username)
                .password(password);
    }

    @Override
    public JsonNode refreshToken(String refreshToken) throws UnirestException {
        String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
        return Unirest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", clientID)
                .field("client_secret", clientSecret)
                .field("refresh_token", refreshToken)
                .field("grant_type", "refresh_token")
                .asJson().getBody();
    }

    @Override
    public User createKeycloakUser(User user, Password password) {
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password.getValue());
        UserRepresentation kcUser = createUserRepresentation(user, credentialRepresentation);
        getUsersResource().create(kcUser);

        UserRepresentation createdUser = searchUserByEmail(user.getEmail());

        UserResource userResource = getUserRessource(createdUser.getId());

        assignRoleToUser(userResource);

        user.setId(createdUser.getId());

        return user;
    }

    @Override
    public void updateKeycloakUser(UserId userId, User user) {
        UserRepresentation userToUpdate = searchUserById(userId);

        userToUpdate.setEmail(user.getEmail().getValue());
        userToUpdate.setUsername(user.getEmail().getValue());
        userToUpdate.setFirstName(user.getUsername());
        userToUpdate.setLastName(user.getNickname());

        UserResource userResource = getUserRessource(userToUpdate.getId());
        userResource.update(userToUpdate);
    }

    @Override
    public void updateKeycloakPassword(User user, Password password) {
        UserRepresentation userToUpdate = searchUserByEmail(user.getEmail());
        userToUpdate.setCredentials(Collections.singletonList(createPasswordCredentials(password.getValue())));

        UserResource userResource = getUserRessource(userToUpdate.getId());

        userResource.update(userToUpdate);
    }

    private void assignRoleToUser(UserResource userResource) {
        String clientUuid = getClientsResource().findByClientId(clientID).get(0).getId();
        RoleRepresentation role = getClientsResource().get(clientUuid).roles().get("USER").toRepresentation();
        userResource.roles().clientLevel(clientUuid).add(Collections.singletonList(role));
    }

    private UserRepresentation createUserRepresentation(User user, CredentialRepresentation credentialRepresentation) {
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail().getValue());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getUsername());
        kcUser.setLastName(user.getNickname());
        kcUser.setEmail(user.getEmail().getValue());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        return kcUser;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private ClientsResource getClientsResource(){
        return getRealmsResource().clients();
    }

    private RealmResource getRealmsResource() {
        return getInstance().realm(realm);
    }

    private UsersResource getUsersResource() {
        return getInstance().realm(realm).users();
    }

    private UserResource getUserRessource(String idUser){
        return getUsersResource().get(idUser);
    }

    private UserRepresentation searchUserByEmail(Email email) {
        return getUsersResource()
                .search(email.getValue())
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private UserRepresentation searchUserById(UserId userId) {
        return getUsersResource()
                .get(userId.getValue().toString())
                .toRepresentation();
    }
}
