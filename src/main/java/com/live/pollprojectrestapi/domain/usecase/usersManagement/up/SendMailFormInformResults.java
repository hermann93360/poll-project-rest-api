package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.domain.model.up.Person;

import java.util.UUID;

public interface SendMailFormInformResults {
    void sendMail(Person person, UUID sessionId);
}
