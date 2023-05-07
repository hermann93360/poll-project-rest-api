package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.application.dto.PollingStationDto;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllPollingStationResponse {
    List<PollingStationDto> pollingStationDtoList;

    public static GetAllPollingStationResponse of(List<PollingStation> pollingStationList) {
        return new GetAllPollingStationResponse(pollingStationList);
    }

    public GetAllPollingStationResponse(List<PollingStation> pollingStationList) {
        pollingStationDtoList = pollingStationList
                .stream()
                .map(PollingStationDto::fromModel)
                .collect(Collectors.toList());
    }
}
