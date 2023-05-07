package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.domain.model.User;
import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

@Data
public class GetUserDetailsResponse {
    private final String userId;
    private final String username;
    private final String nickname;
    private final String email;

    public static GetUserDetailsResponse of(User user){
        return new GetUserDetailsResponse(user);
    }
    public GetUserDetailsResponse(User user) {
        this.userId = user.getId().toString();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail().getValue();
    }
}
