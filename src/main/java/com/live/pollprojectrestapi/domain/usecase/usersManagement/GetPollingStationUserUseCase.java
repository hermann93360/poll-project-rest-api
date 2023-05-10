package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;

import java.util.List;
import java.util.UUID;

public interface GetPollingStationUserUseCase {
    List<PollingStation> getPollingStationUserUseCase(Email ownerEmail);
}
