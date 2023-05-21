package com.live.pollprojectrestapi.application.dto.request.up;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AddGradesInSessionRequest {
    private final String sessionId;
    private final String personId;
    private final List<AddGradeRequest> grades;
}
