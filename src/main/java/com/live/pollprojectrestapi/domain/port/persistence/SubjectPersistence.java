package com.live.pollprojectrestapi.domain.port.persistence;

import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.Subject;

import java.util.UUID;

public interface SubjectPersistence {
    void addSubjectToPollingStation(Subject subject, PollingStation pollingStation);
}
