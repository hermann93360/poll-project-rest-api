package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.PollingStation;

import java.util.UUID;

public interface GetPollingStationById {
    PollingStation getPollingStationById(UUID pollingStationId);
}
