package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.domain.model.up.Person;

public interface GetPersonByEmail {
    Person getPersonByEmail(ManagePersonRequest managePersonRequest);
}
