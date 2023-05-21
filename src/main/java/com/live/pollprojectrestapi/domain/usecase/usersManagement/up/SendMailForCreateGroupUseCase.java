package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.domain.model.up.Person;

import java.util.UUID;

public interface SendMailForCreateGroupUseCase {

    void sendMailForCreateGroup(Person person, UUID groupId, UUID sessionId);
}
