package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.CreateSessionRequest;
import com.live.pollprojectrestapi.domain.model.Email;

import java.util.UUID;

public interface CreateSessionUseCase {
    UUID createSession(Email emailOfUserLogged, CreateSessionRequest createSessionRequest);
}
