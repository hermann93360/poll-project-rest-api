package com.live.pollprojectrestapi.domain.service;

import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.domain.port.persistence.PollingStationPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.SubjectPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PollingStationService implements
        CreatePollingStationUseCase,
        AddSubjectToPollingStationUseCase,
        GetAllPollingStationUseCase,
        GetAdministratorToPollingStation,
        GetPollingStationUserUseCase {

    private PollingStationPersistence pollingStationPersistence;
    private SubjectPersistence subjectPersistence;

    @Override
    public void createPollingStationUseCase(PollingStation pollingStation, Email ownerEmail) {
        pollingStationPersistence.createPollingStation(pollingStation, ownerEmail);
    }

    @Override
    public List<PollingStation> getAll() {
        return pollingStationPersistence.getAllPollingStation();
    }

    @Override
    public void addSubjectToPollingStationUseCase(Subject subject, UUID pollingStationId) {
        Optional<PollingStation> psOwner = pollingStationPersistence.getPollingStationById(pollingStationId);

        if(psOwner.isEmpty()) {
            throw new BadRequestException("this polling station with id " + pollingStationId + " not exist");
        }

        subjectPersistence.addSubjectToPollingStation(subject, psOwner.get());
    }

    @Override
    public User getAdministratorToPollingStation(UUID pollingStationId) {
        Optional<User> administrator = pollingStationPersistence.getAdministrator(pollingStationId);
        if(administrator.isEmpty()) {
            throw new BadRequestException("polling station does not existe");
        }
        return administrator.get();
    }

    @Override
    public List<PollingStation> getPollingStationUserUseCase(Email ownerEmail) {
        return pollingStationPersistence.getPollingStationToUser(ownerEmail);
    }
}
