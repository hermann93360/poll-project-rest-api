package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.PollingStation;

import java.util.List;

public interface GetAllPollingStationUseCase {
    List<PollingStation> getAll();
}
