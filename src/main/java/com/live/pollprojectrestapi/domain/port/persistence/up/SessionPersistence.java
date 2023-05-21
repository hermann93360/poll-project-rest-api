package com.live.pollprojectrestapi.domain.port.persistence.up;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionPersistence {

    UUID createSession(Session session);

    Optional<Session> getSessionByCode(String code);
    Optional<Session> getSession(UUID sessionId);

    UUID addGroupInSession(UUID sessionId, Group groupToAdd, Person firstPersonInGroup);

    Optional<Group> getGroupById(UUID groupId);

    void addGradeInSession(UUID groupId, Grade grade, Person personnWhoGrade);
}
