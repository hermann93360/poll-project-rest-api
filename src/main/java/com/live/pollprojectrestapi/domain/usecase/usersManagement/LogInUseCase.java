package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Login;
import org.keycloak.representations.AccessTokenResponse;

public interface LogInUseCase {

    AccessTokenResponse login(Login login);
}
