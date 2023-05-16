package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.UpdateGroupRequest;

import java.util.UUID;

public interface UpdateGroupInSessionUseCase {
    void updateGroupInSession(UUID groupId, UpdateGroupRequest updateGroupRequest);
}
