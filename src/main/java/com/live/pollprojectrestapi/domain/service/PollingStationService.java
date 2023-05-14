package com.live.pollprojectrestapi.domain.service;

import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.domain.port.persistence.DemandPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.PollingStationPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.SubjectPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        GetPollingStationUserUseCase,
        GetPollingStationById,
        CreateDemandForJoinStationUseCase,
        AddUserInPollingStationUseCase,
        GetDemandNotificationToUserUseCase,
        AcceptDemandForJoinStationUseCase,
        RejectDemandForJoinStationUseCase {

    private PollingStationPersistence pollingStationPersistence;
    private DemandPersistence demandPersistence;
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
        PollingStation psOwner = getPollingStationById(pollingStationId);
        subjectPersistence.addSubjectToPollingStation(subject, psOwner);
    }

    @Override
    public User getAdministratorToPollingStation(UUID pollingStationId) {
        Optional<User> administrator = pollingStationPersistence.getAdministrator(pollingStationId);
        if (administrator.isEmpty()) {
            throw new BadRequestException("polling station does not exist");
        }
        return administrator.get();
    }

    @Override
    public List<PollingStation> getPollingStationUserUseCase(Email ownerEmail) {
        return pollingStationPersistence.getPollingStationToUser(ownerEmail);
    }

    @Override
    public PollingStation getPollingStationById(UUID pollingStationId) {
        Optional<PollingStation> ops = pollingStationPersistence.getPollingStationById(pollingStationId);

        if (ops.isEmpty()) {
            throw new BadRequestException("polling station does not exist");
        }

        return ops.get();
    }

    @Override
    public Demand createDemandForJoinStation(UUID pollingStationId, User requesting, User assignedTo) {
        PollingStation pollingStation = getPollingStationById(pollingStationId);

        checkIfSameDemandExist(pollingStationId, requesting, assignedTo);
        checkIfUserAlreadyInRoom(assignedTo.getEmail(), pollingStation);

        return demandPersistence.addDemandToJoinPollingStation(pollingStation, requesting, assignedTo);
    }

    private void checkIfSameDemandExist(UUID pollingStationId, User requesting, User assignedTo) {
        List<Demand> demands = demandPersistence.getDemandNotificationByUserEmail(assignedTo.getEmail());
        Optional<Demand> oDemand = demands.stream()
                .filter(demand -> demand.getRequesting().getEmail().getValue().equals(requesting.getEmail().getValue()))
                .filter(demand -> demand.getSubjectRequesting().getPollingStationId().equals(pollingStationId))
                .findFirst();

        if (oDemand.isPresent()) {
            throw new BadRequestException("Same demand already exist");
        }
    }

    @Override
    public void addUserInPollingStation(User userToAdd, UUID pollingStationId) {
        PollingStation ps = getPollingStationById(pollingStationId);
        checkIfUserAlreadyInRoom(userToAdd.getEmail(), ps);
        pollingStationPersistence.addUserInPollingStation(userToAdd, ps);
    }

    private void checkIfUserAlreadyInRoom(Email email, PollingStation ps) {
        Optional<User> oUser = ps.getUsersParticipants().stream()
                .filter(user -> user.getEmail().getValue().equals(email.getValue()))
                .findFirst();

        if (oUser.isPresent()) {
            throw new BadRequestException("this user is already in this station");
        }
    }

    @Override
    public List<Demand> getDemandNotificationToUser(Email email) {
        return demandPersistence.getDemandNotificationByUserEmail(email);
    }

    @Override
    public Demand acceptDemandForJoinStation(UUID demandId) {
        Demand demand = checkIfDemandExistAndGet(demandId);

        addUserInPollingStation(demand.getAssignedTo(), demand.getSubjectRequesting().getPollingStationId());
        demandPersistence.removeDemand(demandId);

        return demand;
    }

    @Override
    public void rejectDemandForJoinStation(UUID demandId) {
        checkIfDemandExistAndGet(demandId);
        demandPersistence.removeDemand(demandId);
    }

    private Demand checkIfDemandExistAndGet(UUID demandId) {
        Optional<Demand> oDemand = demandPersistence.getDemandById(demandId);

        if (oDemand.isEmpty()) {
            throw new BadRequestException("this demand does not exist");
        }

        return oDemand.get();
    }
}
