package com.live.pollprojectrestapi.infra.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;
import com.live.pollprojectrestapi.domain.port.persistence.PollingStationPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.UserPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.PollingStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PollingStationJpa implements PollingStationPersistence {

    private PollingStationRepository pollingStationRepository;

    private UserPersistence userPersistence;

    @Override
    public void createPollingStation(PollingStation pollingStation) {
        PollingStationEntity pseToSave = PollingStationEntity.fromModel(pollingStation);
        pollingStationRepository.save(pseToSave);
    }

    @Override
    public List<PollingStation> getAllPollingStation() {
        List<PollingStationEntity> pollingStationEntityList = pollingStationRepository.findAll();

        return pollingStationEntityList
                .stream()
                .map((ps) -> {
                    User administrator = userPersistence.findByUserId(UserId.of(ps.getAdministratorId()));
                    return PollingStation.fromEntity(ps, administrator);
                })
                .collect(Collectors.toList());
    }
}
