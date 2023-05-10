package com.live.pollprojectrestapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Grade {
    private Subject subject;
    private User user;
    private Integer value;
}
