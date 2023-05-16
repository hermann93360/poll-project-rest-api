package com.live.pollprojectrestapi.application.dto.request.up.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetResultResponse {
    private final List<ResultDto> results;
}
