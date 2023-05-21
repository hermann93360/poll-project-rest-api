package com.live.pollprojectrestapi.infra.persistence.up;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;
import com.live.pollprojectrestapi.domain.port.persistence.up.SessionPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.SessionEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.up.GradeRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.up.GroupRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.up.PersonRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.up.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class SessionJpa implements SessionPersistence {

    private SessionRepository sessionRepository;
    private GroupRepository groupRepository;
    private PersonRepository personRepository;
    private GradeRepository gradeRepository;

    @Override
    public UUID createSession(Session session) {
        SessionEntity sessionToSave = SessionEntity.fromModel(session);
        SessionEntity sessionSaved = sessionRepository.save(sessionToSave);
        return sessionSaved.getSessionId();
    }

    @Override
    public Optional<Session> getSessionByCode(String code) {
        List<Session> sessionAll = sessionRepository.findAll().stream().map(Session::fromEntity).collect(Collectors.toList());
        return sessionAll.stream().filter(session -> session.getSessionId().toString().startsWith(code)).findAny();
    }

    @Override
    public Optional<Session> getSession(UUID sessionId) {
        Optional<SessionEntity> getSession = sessionRepository.findById(sessionId);
        return getSession.map(Session::fromEntity);

    }

    @Override
    public UUID addGroupInSession(UUID sessionId, Group groupToAdd, Person firstPersonInGroup) {
        SessionEntity getSession = sessionRepository.findById(sessionId).get();
        GroupEntity group = GroupEntity.fromModel(groupToAdd);
        PersonEntity person = PersonEntity.fromModel(firstPersonInGroup);



        getSession.getGroupsOfSession().add(group);
        group.setAssociatedSession(getSession);
        group.getPersonsInGroup().add(person);
        person.setAssociatedGroup(group);

        sessionRepository.save(getSession);
        GroupEntity save = groupRepository.save(group);
        personRepository.save(person);
        return save.getGroupId();

    }

    @Override
    public Optional<Group> getGroupById(UUID groupId) {
        Optional<GroupEntity> group = groupRepository.findById(groupId);
        return group.map(Group::fromEntity);
    }

    @Override
    public void addGradeInSession(UUID groupId, Grade grade, Person personWhoGrade) {
        GroupEntity group = groupRepository.findById(groupId).get();
        PersonEntity person = personRepository.findByEmail(personWhoGrade.getEmail().getValue()).get();
        GradeEntity gradeToSave = GradeEntity.fromModel(grade);

        group.getGradesOfGroups().add(gradeToSave);
        gradeToSave.setAssociatedGroup(group);
        gradeToSave.setAssignedPerson(person);
        person.getDistributedGrade().add(gradeToSave);

        groupRepository.save(group);
        gradeRepository.save(gradeToSave);
        personRepository.save(person);
    }
}
