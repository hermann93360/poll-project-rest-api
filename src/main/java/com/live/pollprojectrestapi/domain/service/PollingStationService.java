package com.live.pollprojectrestapi.domain.service;

import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.live.pollprojectrestapi.domain.port.persistence.PollingStationPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.CreatePollingStationUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.GetAllPollingStationUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PollingStationService implements
        CreatePollingStationUseCase,
        GetAllPollingStationUseCase {

    private PollingStationPersistence pollingStationPersistence;

    @Override
    public void createPollingStationUseCase(PollingStation pollingStation) {
        pollingStationPersistence.createPollingStation(pollingStation);
    }

    @Override
    public List<PollingStation> getAll() {
        return pollingStationPersistence.getAllPollingStation();
    }

}
