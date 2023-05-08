package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.application.configuration.SecurityContextUtils;
import com.live.pollprojectrestapi.application.dto.request.CreateUserRequest;
import com.live.pollprojectrestapi.application.dto.request.LoginRequest;
import com.live.pollprojectrestapi.application.dto.response.GetUserDetailsResponse;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.application.dto.UserDto;
import com.live.pollprojectrestapi.domain.port.provider.KeycloakProviderPort;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.CreateUserUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.GetUserDetailsUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.LogInUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.UpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class AuthentificationController {

    private final LogInUseCase logInUseCase;

    private final CreateUserUseCase createUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    private final GetUserDetailsUseCase getUserDetailsUseCase;

    private final KeycloakProviderPort keycloakProviderPort;

    @PostMapping("/login")
    public AccessTokenResponse login(@RequestBody LoginRequest loginRequest) {
        Login login = Login.builder()
                .username(loginRequest.getUsername())
                .password(loginRequest.getPassword())
                .build();

        return logInUseCase.login(login);
    }

    @PostMapping("/users/create")
    public void createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.getUsername())
                .nickname(createUserRequest.getNickname())
                .email(Email.of(createUserRequest.getEmail()))
                .build();

        Password password = Password.of(createUserRequest.getPassword());

        createUserUseCase.createUser(user, password);
    }

    @PatchMapping("/user/update")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public void updateUserEmail(
            @RequestBody UserDto userDto,
            @RequestParam String idUser) {

        User user = User.builder()
                .id(UUID.fromString(idUser))
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(Email.of(userDto.getEmail()))
                .build();

        UserId userId = UserId.of(idUser);

        updateUserUseCase.updateUser(userId, user);

    }

    @GetMapping("/user/details")
    public GetUserDetailsResponse getUserDetails() {
        Email ownerEmail = Email.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return GetUserDetailsResponse.of(getUserDetailsUseCase.getUserDetails(ownerEmail));
    }

    @GetMapping("/protected")
    public GetUserDetailsResponse getProtected(HttpServletRequest request) {

        Email ownerEmail = Email.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return GetUserDetailsResponse.of(getUserDetailsUseCase.getUserDetails(ownerEmail));
    }

}
