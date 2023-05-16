package com.live.pollprojectrestapi.domain.model.up;

import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Person {
    private UUID personId;
    private String name;
    private Email email;


    public static Person of(String name, String email) {
        return Person.builder()
                .name(name)
                .email(Email.of(email))
                .build();
    }

    public static Person fromEntity(PersonEntity personEntity) {
        return Person.builder()
                .personId(personEntity.getPersonId())
                .name(personEntity.getName())
                .email(Email.of(personEntity.getEmail()))
                .build();
    }

    public static Person fromRequest(ManagePersonRequest managePersonRequest) {
        return Person.builder()
                .email(Email.of(checkEmptyOrNull(managePersonRequest.getEmailOfRequesting())))
                .name(checkEmptyOrNull(managePersonRequest.getNameOfRequesting()))
                .build();
    }

    private static String checkEmptyOrNull(String value) {
        if(value == null || value.isEmpty()) {
            throw new BadRequestException("person is not setted for join session");
        }
        return value;
    }
}
