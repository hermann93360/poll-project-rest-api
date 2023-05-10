package com.live.pollprojectrestapi.infra.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.live.pollprojectrestapi.domain.port.persistence.PollingStationPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.UserPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.UserEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.PollingStationRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PollingStationJpa implements PollingStationPersistence {

    private PollingStationRepository pollingStationRepository;
    private UserRepository userRepository;


    @Override
    public void createPollingStation(PollingStation pollingStation, Email ownerEmail) {
        UserEntity user = userRepository.findByEmail(ownerEmail.getValue()).orElseThrow();
        PollingStationEntity pseToSave = PollingStationEntity.fromModel(pollingStation);

        user.getPollingStations().add(pseToSave);
        pseToSave.setAdministrator(user);

        pollingStationRepository.save(pseToSave);
    }

    @Override
    public List<PollingStation> getAllPollingStation() {
        List<PollingStationEntity> pollingStationEntityList = pollingStationRepository.findAll();

        return pollingStationEntityList
                .stream()
                .map(PollingStation::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PollingStation> getPollingStationById(UUID pollingStationId) {
        Optional<PollingStationEntity> opse = pollingStationRepository.findById(pollingStationId);
        return opse.map(PollingStation::fromEntity);
    }

    @Override
    public Optional<User> getAdministrator(UUID pollingStationId) {
        Optional<PollingStationEntity> opse = pollingStationRepository.findById(pollingStationId);
        return opse.map(pollingStationEntity -> User.fromEntity(pollingStationEntity.getAdministrator()));
    }

    @Override
    public List<PollingStation> getPollingStationToUser(Email ownerEmail) {
        List<PollingStationEntity> pses = pollingStationRepository.findAllByAdministratorEmail(ownerEmail.getValue());
        return pses.stream()
                .map(PollingStation::fromEntity)
                .collect(Collectors.toList());
    }
}
