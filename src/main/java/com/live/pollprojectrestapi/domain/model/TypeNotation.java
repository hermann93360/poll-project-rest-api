package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

public enum TypeNotation {
    string("string");

    private final String value;

    TypeNotation(String value) {
        this.value = value;
    }
}
