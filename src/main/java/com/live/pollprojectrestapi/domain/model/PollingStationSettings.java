package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PollingStationSettings {
    private TypeNotation typeNotation;
    private boolean notationVisible;
    private Integer userLimit;
    private Scope scope;
    private String password;
    private PollType pollType;
}
