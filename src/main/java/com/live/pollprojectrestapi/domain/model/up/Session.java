package com.live.pollprojectrestapi.domain.model.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class Session {
    private UUID sessionId;
    private String name;
    private List<Group> groupsOfSession;
    private String description;
    private String keywords;
    private boolean active;
    private Integer countGroup;
    private Integer countParticipants;
    private String scope;

    public static Session fromEntity(SessionEntity sessionEntity) {
        return Session.builder()
                .sessionId(sessionEntity.getSessionId())
                .name(sessionEntity.getName())
                .groupsOfSession(mapGroupEntities(sessionEntity.getGroupsOfSession()))
                .description(sessionEntity.getDescription())
                .keywords(sessionEntity.getKeywords())
                .active(sessionEntity.isActive())
                .countGroup(sessionEntity.getCountGroup())
                .countParticipants(sessionEntity.getCountParticipants())
                .scope(sessionEntity.getScope())
                .build();
    }

    private static List<Group> mapGroupEntities(List<GroupEntity> groupEntities) {
        return groupEntities.stream()
                .map(Group::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean isPrivate() {
        return scope.equalsIgnoreCase("PRIVATE");
    }

    public boolean allGroupHaveSameGradeCount() {
        int sizeOfGrades = groupsOfSession.stream().findFirst().get().getGradesOfGroups().size();
        return groupsOfSession.stream()
                .map(group -> group.getGradesOfGroups().size())
                .allMatch(size -> size == sizeOfGrades);
    }

    public boolean personAlreadyGraded(UUID personId) {
        return groupsOfSession
                .stream()
                .anyMatch(group -> group.isGraded(personId));
    }

    public boolean isActivate() {
        return groupsOfSession
                .stream()
                .allMatch(Group::isConfigured);
    }

    public int countOfPersonsInAllGroups() {
        return groupsOfSession
                .stream()
                .mapToInt(Group::countOfPersons)
                .sum();
    }

    public boolean canGetResult() {
        return groupsOfSession
                .stream()
                .map(Group::getGradesOfGroups)
                .allMatch(grades -> grades.size() == countOfPersonsInAllGroups());
    }

    public boolean personHasAlreadyGrade(Person person){
        return groupsOfSession.stream().anyMatch(group -> group.gradeExist(person.getEmail()));
    }
}
