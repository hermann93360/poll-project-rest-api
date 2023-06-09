package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.domain.model.up.Person;

import java.util.UUID;

public interface AddGroupInSessionUseCase {
    UUID addGroupInSessionUseCase(UUID sessionId, Person person);
}
