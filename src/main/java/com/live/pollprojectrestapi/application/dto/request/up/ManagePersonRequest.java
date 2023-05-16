package com.live.pollprojectrestapi.application.dto.request.up;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ManagePersonRequest {
    private final String nameOfRequesting;
    private final String emailOfRequesting;
}
