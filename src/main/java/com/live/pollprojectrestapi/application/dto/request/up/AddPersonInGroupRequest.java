package com.live.pollprojectrestapi.application.dto.request.up;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddPersonInGroupRequest {
    private final String name;
    private final String email;
}
