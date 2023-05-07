package com.live.pollprojectrestapi.domain.port.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;

import java.util.List;

public interface PollingStationPersistence {

    void createPollingStation(PollingStation pollingStation);

    List<PollingStation> getAllPollingStation();
}
