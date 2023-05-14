package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Demand;

import java.util.UUID;

public interface AcceptDemandForJoinStationUseCase {
    Demand acceptDemandForJoinStation(UUID demandId);
}
