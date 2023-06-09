package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;

import java.util.UUID;

public interface GetSessionUseCase {
    Session getSession(String code,  ManagePersonRequest managePersonRequest);
}
