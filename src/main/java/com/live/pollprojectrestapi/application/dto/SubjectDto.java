package com.live.pollprojectrestapi.application.dto;

import com.live.pollprojectrestapi.domain.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SubjectDto {
    private String name;
    private String pathImg;
    private String description;

    public static SubjectDto fromModel(Subject subject) {
        return SubjectDto.builder()
                .name(subject.getName())
                .description(subject.getDescription())
                .pathImg(subject.getPathImg())
                .build();
    }
}
