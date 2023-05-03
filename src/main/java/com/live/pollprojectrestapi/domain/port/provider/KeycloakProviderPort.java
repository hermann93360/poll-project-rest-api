package com.live.pollprojectrestapi.domain.port.provider;

import com.live.pollprojectrestapi.domain.model.Password;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import com.mashape.unirest.http.JsonNode;

public interface KeycloakProviderPort {

    Keycloak getInstance();

    KeycloakBuilder getInstanceWithPassword(String username, String password);

    JsonNode refreshToken(String refreshToken) throws UnirestException;

    User createKeycloakUser(User user, Password password);

    void updateKeycloakUser(UserId userId, User updateUser);

    void updateKeycloakPassword(User user, Password password);
}
