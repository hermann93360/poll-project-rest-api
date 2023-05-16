package com.live.pollprojectrestapi.infra.persistence.entity.up;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID personId;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupEntity associatedGroup;

    @OneToMany(mappedBy = "assignedPerson")
    private List<GradeEntity> distributedGrade;

    public static PersonEntity fromModel(Person person) {
        return PersonEntity.builder()
                .personId(person.getPersonId())
                .name(person.getName())
                .email(person.getEmail().getValue())
                .build();
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
