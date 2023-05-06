package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.domain.model.User;
import org.keycloak.representations.AccessTokenResponse;

public class UserLoginResponse {
    private final AccessTokenResponse accessTokenResponse;
    private final String userId;
    private final String username;
    private final String nickname;
    private final String email;

    public UserLoginResponse(AccessTokenResponse accessTokenResponse, User user) {
        this.accessTokenResponse = accessTokenResponse;
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail().getValue();
    }
}
