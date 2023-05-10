package com.live.pollprojectrestapi.domain.model;

import com.live.pollprojectrestapi.application.dto.request.AddSubjectToPollingStationRequest;
import com.live.pollprojectrestapi.infra.persistence.entity.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Subject {
    private String name;
    private String pathImg;
    private String description;

    public static Subject fromRequest(AddSubjectToPollingStationRequest request) {
        return Subject.builder()
                .name(request.getName())
                .pathImg(request.getPathImg())
                .description(request.getDescription())
                .build();
    }

    public static Subject fromEntity(SubjectEntity entity) {
        return Subject.builder()
                .name(entity.getName())
                .pathImg(entity.getPathImg())
                .description(entity.getDescription())
                .build();
    }
}
