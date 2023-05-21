package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.domain.model.up.Session;

import java.util.UUID;

public interface CreateResultUseCase {
    Session createResultAndGet(UUID sessionId);
}
