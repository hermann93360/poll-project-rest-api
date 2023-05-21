package com.live.pollprojectrestapi.infra.persistence.entity.up;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID groupId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "sessionId")
    private SessionEntity associatedSession;

    @OneToMany(mappedBy = "associatedGroup")
    private List<PersonEntity> personsInGroup;

    @OneToMany(mappedBy = "associatedGroup")
    private List<GradeEntity> gradesOfGroups;

    private double average;

    private boolean configured;

    public static GroupEntity fromModel(Group group) {
        return GroupEntity.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .personsInGroup(group.getPersonsInGroup() == null ? new ArrayList<>() : mapPersonModels(group.getPersonsInGroup()))
                .gradesOfGroups(group.getGradesOfGroups() == null ? new ArrayList<>() : mapGradeModels(group.getGradesOfGroups()))
                .average(group.getAverage())
                .configured(group.isConfigured())
                .build();
    }

    private static List<PersonEntity> mapPersonModels(List<Person> persons) {
        return persons.stream()
                .map(PersonEntity::fromModel)
                .collect(Collectors.toList());
    }

    private static List<GradeEntity> mapGradeModels(List<Grade> grades) {
        return grades.stream()
                .map(GradeEntity::fromModel)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", average=" + average +
                ", configured=" + configured +
                '}';
    }
}
