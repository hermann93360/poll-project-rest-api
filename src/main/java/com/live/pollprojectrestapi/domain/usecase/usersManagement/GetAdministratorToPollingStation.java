package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.User;

import java.util.UUID;

public interface GetAdministratorToPollingStation {
    User getAdministratorToPollingStation(UUID pollingStationId);
}
