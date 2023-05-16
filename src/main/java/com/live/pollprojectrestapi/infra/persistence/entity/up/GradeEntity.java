package com.live.pollprojectrestapi.infra.persistence.entity.up;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "grade")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID gradeId;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private GroupEntity associatedGroup;

    @ManyToOne
    @JoinColumn(name = "personId")
    private PersonEntity assignedPerson;

    private Integer note;

    public static GradeEntity fromModel(Grade grade) {
        return GradeEntity.builder()
                .gradeId(grade.getGradeId() != null ? grade.getGradeId() : null)
                .assignedPerson(grade.getAssignedPerson() != null ? PersonEntity.fromModel(grade.getAssignedPerson()) : null)
                .note(grade.getNote())
                .build();
    }

    @Override
    public String toString() {
        return "GradeEntity{" +
                "gradeId=" + gradeId +
                ", note=" + note +
                '}';
    }
}
