package com.live.pollprojectrestapi.domain.port.persistence;

import com.live.pollprojectrestapi.domain.model.Demand;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemandPersistence {

    Demand addDemandToJoinPollingStation(PollingStation pollingStation, User requesting, User assignedTo);

    List<Demand> getDemandNotificationByUserEmail(Email email);

    Optional<Demand> getDemandById(UUID demandID);

    void removeDemand(UUID demandId);
}
