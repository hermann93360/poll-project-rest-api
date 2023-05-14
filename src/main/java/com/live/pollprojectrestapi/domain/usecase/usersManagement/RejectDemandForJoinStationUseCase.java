package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import java.util.UUID;

public interface RejectDemandForJoinStationUseCase {
    void rejectDemandForJoinStation(UUID demandId);
}
