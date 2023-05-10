package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Subject;

import java.util.UUID;

public interface AddSubjectToPollingStationUseCase {
    void addSubjectToPollingStationUseCase(Subject subject, UUID pollingStationId);
}
