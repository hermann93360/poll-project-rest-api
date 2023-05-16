package com.live.pollprojectrestapi.application.dto.request.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CreateGroupRequest {
    private final String emailOfFirst;
    private final String nameOfFirst;
}
