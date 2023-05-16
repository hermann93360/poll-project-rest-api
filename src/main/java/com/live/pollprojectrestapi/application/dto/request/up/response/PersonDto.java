package com.live.pollprojectrestapi.application.dto.request.up.response;

import com.live.pollprojectrestapi.domain.model.up.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PersonDto {
    private final String name;
    private final String email;

    public static PersonDto fromPerson(Person person) {
        return PersonDto.builder()
                .name(person.getName())
                .email(person.getEmail().getValue())
                .build();
    }

}
