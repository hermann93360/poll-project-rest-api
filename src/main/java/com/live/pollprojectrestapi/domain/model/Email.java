package com.live.pollprojectrestapi.domain.model;

import lombok.Getter;

import java.util.Objects;

public class Email {

    @Getter
    private final String value;

    public static Email of(String email) {
        return new Email(email);
    }

    public Email(String email) {
        this.value = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
