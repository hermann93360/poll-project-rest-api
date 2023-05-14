package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Demand;
import com.live.pollprojectrestapi.domain.model.Email;

import java.util.List;

public interface GetDemandNotificationToUserUseCase {
    List<Demand> getDemandNotificationToUser(Email email);
}
