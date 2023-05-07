package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.domain.model.Email;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static Email getUserEmailPrincipal() {
        return Email.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
