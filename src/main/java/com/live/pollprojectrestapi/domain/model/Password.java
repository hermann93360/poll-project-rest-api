package com.live.pollprojectrestapi.domain.model;

import lombok.Data;
import lombok.Getter;

public class Password {
    @Getter
    private final String value;

    public static Password of(String password) {
        return new Password(password);
    }

    public Password(String password) {
        this.value = password;
    }
}
