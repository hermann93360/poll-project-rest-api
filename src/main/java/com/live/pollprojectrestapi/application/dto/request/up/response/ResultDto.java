package com.live.pollprojectrestapi.application.dto.request.up.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class ResultDto {
    private final String nameOfGroup;
    private final double average;
    private final List<GradeDto> grades;
}
