package com.live.pollprojectrestapi.domain.model;

import lombok.Getter;

import java.util.UUID;

public class UserId {
    @Getter
    private final UUID value;

    public static UserId of(String id) {
        return new UserId(UUID.fromString(id));
    }

    public static UserId of(UUID id) {
        return new UserId(id);
    }


    public UserId(UUID id) {
        this.value = id;
    }

}
