package com.live.pollprojectrestapi.domain.model.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Grade {
    private UUID gradeId;
    private Person assignedPerson;
    private Integer note;

    public static Grade of(Integer note) {
        return Grade.builder()
                .note(note)
                .build();
    }

    public static Grade fromEntity(GradeEntity gradeEntity) {
        return Grade.builder()
                .gradeId(gradeEntity.getGradeId())
                .assignedPerson(Person.fromEntity(gradeEntity.getAssignedPerson()))
                .note(gradeEntity.getNote())
                .build();
    }
}
