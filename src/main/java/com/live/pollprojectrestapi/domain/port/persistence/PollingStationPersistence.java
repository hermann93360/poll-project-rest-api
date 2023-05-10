package com.live.pollprojectrestapi.domain.port.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PollingStationPersistence {

    void createPollingStation(PollingStation pollingStation, Email ownerEmail);

    List<PollingStation> getAllPollingStation();

    Optional<PollingStation> getPollingStationById(UUID pollingStationId);

    Optional<User> getAdministrator(UUID pollingStationId);

    List<PollingStation> getPollingStationToUser(Email ownerEmail);
}
