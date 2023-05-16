package com.live.pollprojectrestapi.domain.model.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Group {
    private UUID groupId;
    private String name;
    private List<Person> personsInGroup;
    private List<Grade> gradesOfGroups;
    private float average;
    private boolean configured;

    public static Group fromEntity(GroupEntity groupEntity) {
        return Group.builder()
                .groupId(groupEntity.getGroupId())
                .name(groupEntity.getName())
                .personsInGroup(mapPersonEntities(groupEntity.getPersonsInGroup()))
                .gradesOfGroups(mapGradeEntities(groupEntity.getGradesOfGroups()))
                .average(groupEntity.getAverage())
                .configured(groupEntity.isConfigured())
                .build();
    }

    private static List<Person> mapPersonEntities(List<PersonEntity> personEntities) {
        return personEntities.stream()
                .map(Person::fromEntity)
                .collect(Collectors.toList());
    }

    private static List<Grade> mapGradeEntities(List<GradeEntity> gradeEntities) {
        return gradeEntities.stream()
                .map(Grade::fromEntity)
                .collect(Collectors.toList());
    }

    public static Group emptyGroup() {
        return new Group();
    }

    public void isSet() {
        configured = true;
    }
}
