package com.live.pollprojectrestapi.domain.model;

import lombok.Getter;

import java.util.UUID;

public class UserId {
    @Getter
    private final UUID value;

    public static UserId of(String id) {
        return new UserId(id);
    }

    public UserId(String id) {
        this.value = UUID.fromString(id);
    }

}
