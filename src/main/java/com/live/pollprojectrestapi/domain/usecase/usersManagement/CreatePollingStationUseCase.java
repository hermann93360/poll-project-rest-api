package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.UserId;

public interface CreatePollingStationUseCase {
    void createPollingStationUseCase(PollingStation pollingStation);
}
