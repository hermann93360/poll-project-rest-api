package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.User;

import java.util.UUID;

public interface AddUserInPollingStationUseCase {
    void addUserInPollingStation(User userToAdd, UUID pollingStationId);


}
