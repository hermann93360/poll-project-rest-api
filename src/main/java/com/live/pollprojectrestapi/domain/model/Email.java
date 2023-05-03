package com.live.pollprojectrestapi.domain.model;

import lombok.Getter;

public class Email {

    @Getter
    private final String value;

    public static Email of(String email) {
        return new Email(email);
    }

    public Email(String email) {
        this.value = email;
    }
}
