package com.live.pollprojectrestapi.application.dto.request.up;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddGradeRequest {
    private final String groupId;
    private final String personId;
    private final Integer grade;
}
