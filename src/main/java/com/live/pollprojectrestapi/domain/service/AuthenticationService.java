package com.live.pollprojectrestapi.domain.service;

import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.domain.port.persistence.UserPersistence;
import com.live.pollprojectrestapi.domain.port.provider.KeycloakProviderPort;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.CreateUserUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.GetUserDetailsUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.LogInUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.UpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements
        CreateUserUseCase,
        UpdateUserUseCase,
        LogInUseCase,
        GetUserDetailsUseCase {

    private final KeycloakProviderPort keycloakProviderPort;

    private final UserPersistence userPersistence;

    @Override
    public AccessTokenResponse login(Login login) {
        Keycloak keycloak = keycloakProviderPort.getInstanceWithPassword(login.getUsername(), login.getPassword()).build();
        return keycloak.tokenManager().getAccessToken();
    }

    @Override
    public void createUser(User user, Password password) {
        checkIfEmailNotExist(user);

        User userToCreate = keycloakProviderPort.createKeycloakUser(user, password);
        userPersistence.createUser(userToCreate);
    }

    @Override
    public void updateUser(UserId userId, User user) {
        checkIfEmailNotExist(user);

        keycloakProviderPort.updateKeycloakUser(userId, user);
        userPersistence.createUser(user);
    }
    @Override
    public User getUserDetails(Email email) {
        Optional<User> user = userPersistence.findByEmail(email);

        if(user.isEmpty()) {
            throw new BadRequestException("User does exist");
        }

        return user.get();
    }


    private void checkIfEmailNotExist(User user) {
        if(userPersistence.findByEmail(user.getEmail()).isPresent())
            throw new BadRequestException("This mail is already used");
    }
}
