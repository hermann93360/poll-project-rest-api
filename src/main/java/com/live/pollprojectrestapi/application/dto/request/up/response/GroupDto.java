package com.live.pollprojectrestapi.application.dto.request.up.response;

import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class GroupDto {
    private final UUID groupId;
    private final String name;
    private final List<PersonDto> personsInGroup;

    public static GroupDto fromGroup(Group group) {

        return GroupDto.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .personsInGroup(mapPersonDto(group.getPersonsInGroup()))
                .build();
    }

    private static List<PersonDto> mapPersonDto(List<Person> persons) {
        return persons.stream()
                .map(PersonDto::fromPerson)
                .collect(Collectors.toList());
    }
}
