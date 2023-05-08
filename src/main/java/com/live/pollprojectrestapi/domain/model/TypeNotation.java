package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

public enum TypeNotation {
    STAR("STAR"),
    SCALE10("SCALE10"),
    SCALE20("SCALE20"),
    LETTER("LETTER");

    private final String value;

    TypeNotation(String value) {
        this.value = value;
    }
}
