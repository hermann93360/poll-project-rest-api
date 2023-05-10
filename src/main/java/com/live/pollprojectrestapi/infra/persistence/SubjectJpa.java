package com.live.pollprojectrestapi.infra.persistence;

import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.Subject;
import com.live.pollprojectrestapi.domain.port.persistence.SubjectPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.SubjectEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.PollingStationRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class SubjectJpa implements SubjectPersistence {

    private SubjectRepository subjectRepository;

    @Override
    public void addSubjectToPollingStation(Subject subject, PollingStation pollingStationId) {
        SubjectEntity se = SubjectEntity.fromModel(subject);
        PollingStationEntity pse = PollingStationEntity.fromModel(pollingStationId);

        pse.getSubjects().add(se);
        se.setPollingStation(pse);

        subjectRepository.save(se);
    }
}
