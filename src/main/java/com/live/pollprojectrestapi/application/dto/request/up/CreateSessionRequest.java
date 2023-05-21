package com.live.pollprojectrestapi.application.dto.request.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class CreateSessionRequest {
    private final String name;
    private final String scope;
    private final String description;
    private final String keywords;
    private final Integer countParticipants;
}
