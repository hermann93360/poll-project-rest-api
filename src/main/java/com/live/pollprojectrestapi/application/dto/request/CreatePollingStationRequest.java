package com.live.pollprojectrestapi.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CreatePollingStationRequest {
    private String name;
    private String category;
    private String description;
    private String keywords;
    private String typeNotation;
    private boolean notationVisible;
    private Integer userLimit;
    private String scope;
    private String password;
    private String pollType;
    private LocalDateTime start;
    private LocalDateTime end;
}
