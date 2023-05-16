package com.live.pollprojectrestapi.application.dto.request.up.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GradeDto {
    private final String nameAssigned;
    private final Integer note;
}
