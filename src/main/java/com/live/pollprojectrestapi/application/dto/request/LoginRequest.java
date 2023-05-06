package com.live.pollprojectrestapi.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
