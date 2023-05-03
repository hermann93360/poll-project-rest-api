package com.live.pollprojectrestapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
}
