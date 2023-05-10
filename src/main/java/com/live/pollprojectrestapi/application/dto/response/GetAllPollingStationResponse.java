package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.application.dto.PollingStationDto;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllPollingStationResponse {
    List<PollingStationDto> pollingStationDtoList;

    public static GetAllPollingStationResponse of(List<PollingStation> pollingStationList, List<User> administrators) {
        return new GetAllPollingStationResponse(pollingStationList, administrators);
    }

    public GetAllPollingStationResponse(List<PollingStation> pollingStationList, List<User> administrators) {
        pollingStationDtoList = pollingStationList
                .stream()
                .map(PollingStationDto::fromModel)
                .collect(Collectors.toList());

        for(PollingStationDto p : pollingStationDtoList) {
            int index = pollingStationDtoList.indexOf(p);
            p.setAdministratorId(administrators.get(index).getId());
            p.setNameOfAdministrator(administrators.get(index).getUsername());
        }
    }
}
