package com.live.pollprojectrestapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginDto {
    private String username;
    private String password;
}
