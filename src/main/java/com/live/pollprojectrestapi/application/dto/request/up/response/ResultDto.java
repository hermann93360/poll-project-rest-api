package com.live.pollprojectrestapi.application.dto.request.up.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ResultDto {
    private final String nameOfGroup;
    private final String average;
    private final List<GradeDto> grades;
}
