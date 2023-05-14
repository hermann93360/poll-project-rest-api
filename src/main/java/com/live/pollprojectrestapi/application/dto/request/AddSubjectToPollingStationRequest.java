package com.live.pollprojectrestapi.application.dto.request;

import com.live.pollprojectrestapi.domain.model.PollingStation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class AddSubjectToPollingStationRequest {
    private String name;
    private String pathImg;
    private String description;
}
