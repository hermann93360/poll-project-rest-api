package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.AddGradesInSessionRequest;

import java.util.UUID;

public interface GradeSessionUseCase {
    void gradeSessionUseCase(UUID sessionId, AddGradesInSessionRequest addGradesInSessionRequest);
}
