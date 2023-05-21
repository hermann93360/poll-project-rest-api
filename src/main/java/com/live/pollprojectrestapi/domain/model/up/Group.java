package com.live.pollprojectrestapi.domain.model.up;

import com.live.pollprojectrestapi.application.dto.request.up.response.ResultDto;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.Result;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Group {
    private UUID groupId;
    private String name;
    private List<Person> personsInGroup;
    private List<Grade> gradesOfGroups;
    private double average;
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

    public ResultDto buildResult() {
        return ResultDto.builder()
                .nameOfGroup(name)
                .average(average)
                .grades(gradesOfGroups.stream().map(Grade::toDto).collect(Collectors.toList()))
                .build();
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

    public Integer countOfGrades() {
        return gradesOfGroups.size();
    }
    public Integer countOfPersons() {
        return personsInGroup.size();
    }

    public void getCalcAverage() {
        OptionalDouble od = gradesOfGroups
                .stream()
                .map(Grade::getNote)
                .mapToInt(Integer::intValue)
                .average();

        if(od.isPresent()) {
            average = od.getAsDouble();
        }
    }

    public boolean isGraded(UUID personId) {
        return gradesOfGroups.stream().anyMatch(grade -> grade.getAssignedPerson().getPersonId().equals(personId));
    }

    public boolean gradeExist(Email email){
        return gradesOfGroups.stream().anyMatch(grade -> grade.getAssignedPerson().getEmail().equals(email));
    }

    public boolean personInGroup(Person personToGet) {
        return personsInGroup.stream().noneMatch(person -> person.getEmail().equals(personToGet.getEmail()));
    }
}
