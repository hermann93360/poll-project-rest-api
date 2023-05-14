package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.application.dto.PollingStationDto;
import com.live.pollprojectrestapi.application.dto.UserDetailsDto;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetPollingStationResponse {
    private PollingStationDto pollingStation;

    public static GetPollingStationResponse of(PollingStation ps, User administrator) {
        return new GetPollingStationResponse(ps, administrator);
    }

    public GetPollingStationResponse(PollingStation ps, User administrator) {
        pollingStation = PollingStationDto.fromModel(ps);
        pollingStation.setAdministratorId(administrator.getId());
        pollingStation.setNameOfAdministrator(administrator.getUsername());
    }
}
