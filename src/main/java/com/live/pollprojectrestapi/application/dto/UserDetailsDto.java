package com.live.pollprojectrestapi.application.dto;

import com.live.pollprojectrestapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class UserDetailsDto {
    private final UUID userId;
    private final String username;
    private final String nickname;
    private final String email;

    public static UserDetailsDto fromModel(User user){
        return UserDetailsDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail().getValue())
                .build();
    }

}

