package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {
    private UUID id;
    private String username;
    private String nickname;
    private Email email;

    public void replace(User user) {
        id = user.id;
        username = user.getUsername();
        nickname = user.getNickname();
        email = user.getEmail();
    }
}
