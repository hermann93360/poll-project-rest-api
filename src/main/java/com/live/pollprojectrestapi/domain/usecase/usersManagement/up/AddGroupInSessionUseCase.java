package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.CreateGroupRequest;
import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.domain.model.up.Person;

import java.util.UUID;

public interface AddGroupInSessionUseCase {
    void addGroupInSessionUseCase(UUID sessionId, Person person);
}
