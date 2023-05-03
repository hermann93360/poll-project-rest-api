package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.application.dto.LoginDto;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.application.dto.UserDto;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.CreateUserUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.LogInUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.UpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api")
public class AuthentificationController {

    private final LogInUseCase logInUseCase;

    private final CreateUserUseCase createUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/login")
    public AccessTokenResponse login(@RequestBody LoginDto loginDto) {
        Login login = Login.builder()
                .username(loginDto.getUsername())
                .password(loginDto.getPassword())
                .build();

        return logInUseCase.login(login);
    }

    @PostMapping("/users/create")
    public void createUser(@RequestBody UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(Email.of(userDto.getEmail()))
                .build();

        Password password = Password.of(userDto.getPassword());

        createUserUseCase.createUser(user, password);
    }

    @PatchMapping("/user/update")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public void updateUserEmail(
            @RequestBody UserDto userDto,
            @RequestParam String idUser) {

        User user = User.builder()
                .id(idUser)
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(Email.of(userDto.getEmail()))
                .build();

        UserId userId = UserId.of(idUser);

        updateUserUseCase.updateUser(userId, user);

    }

}
