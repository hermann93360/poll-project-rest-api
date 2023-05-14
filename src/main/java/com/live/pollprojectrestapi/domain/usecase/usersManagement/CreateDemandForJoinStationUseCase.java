package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Demand;
import com.live.pollprojectrestapi.domain.model.User;

import java.util.UUID;

public interface CreateDemandForJoinStationUseCase {
    Demand createDemandForJoinStation(UUID pollingStationId, User requesting, User assignedTo);
}
