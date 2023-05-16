package com.live.pollprojectrestapi.domain.service.up;

import com.live.pollprojectrestapi.application.dto.request.up.*;
import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;
import com.live.pollprojectrestapi.domain.port.persistence.up.GroupPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.PersonPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.SessionPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.*;
import com.live.pollprojectrestapi.infra.persistence.repository.up.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SessionService implements
        CreateSessionUseCase,
        GetSessionUseCase,
        GetOrCreatePersonByEmailUseCase,
        AddGroupInSessionUseCase,
        GradeSessionUseCase {

    private SessionPersistence sessionPersistence;
    private PersonPersistence personPersistence;
    private GroupPersistence groupPersistence;

    @Override
    public UUID createSession(Email emailOfUserLogged, CreateSessionRequest createSessionRequest) {
        Session sessionToAdd = buildSessionFromRequest(createSessionRequest);
        return sessionPersistence.createSession(sessionToAdd);
    }

    private Session buildSessionFromRequest(CreateSessionRequest createSessionRequest) {
        return Session.builder()
                .name(createSessionRequest.getName())
                .scope(createSessionRequest.getScope())
                .description(createSessionRequest.getDescription())
                .keywords(createSessionRequest.getKeywords())
                .countParticipants(createSessionRequest.getCountParticipants())
                .groupsOfSession(Collections.emptyList())
                .build();
    }

    @Override
    public Session getSession(UUID sessionId, Person personToGet) {
        Optional<Session> getSession = sessionPersistence.getSession(sessionId);
        Session session = checkAndGetSession(getSession);

        if (session.isPrivate()) {
            // checkIfPersonIsAuthorizedToJoinSession(session, personToGet);
        }

        return session;
    }

    @Override
    public Person getOrCreatePersonByEmail(ManagePersonRequest managePersonRequest) {
        Person personToGet = Person.fromRequest(managePersonRequest);

        Optional<Person> person = personPersistence.getPerson(Email.of(managePersonRequest.getEmailOfRequesting()));

        return person.orElseGet(() -> personPersistence.createPerson(personToGet));
    }

    @Override
    public void addGroupInSessionUseCase(UUID sessionId, Person firstPersonIdGroup) {

        checkIfSessionExist(sessionPersistence.getSession(sessionId));

        Group groupToAdd = Group.emptyGroup();

        sessionPersistence.addGroupInSession(sessionId, groupToAdd, firstPersonIdGroup);

    }

    @Override
    public void gradeSessionUseCase(UUID sessionId, AddGradesInSessionRequest addGradesInSessionRequest) {
        Session session = checkAndGetSession(sessionPersistence.getSession(sessionId));

        addGradesInSessionRequest.getGrades().forEach(
                grade -> {
                    Group group = checkIfGroupExistAndGet(
                            sessionPersistence.getGroupById(UUID.fromString(grade.getGroupId()))
                    );

                    Person person = checkIfPersonExist(
                            personPersistence.getPersonById(UUID.fromString(grade.getPersonId()))
                    );

                    Grade gradeToSave = Grade.of(grade.getGrade());

                    sessionPersistence.addGradeInSession(UUID.fromString(grade.getGroupId()), gradeToSave, person);

                });
    }

    private Group checkIfGroupExistAndGet(Optional<Group> groups) {
        if(groups.isEmpty()) {
            throw new BadRequestException("this group does not exist");
        }
        return groups.get();
    }

    private void checkIfPersonIsAuthorizedToJoinSession(Session session, Person personToGet) {
        if (session.getGroupsOfSession().stream()
                .filter(group -> group.getPersonsInGroup()
                        .stream()
                        .anyMatch(person -> person.getEmail().equals(personToGet.getEmail()))
                ).findFirst().isEmpty()) {
            throw new BadRequestException("this user is not authorized to join this session");
        }
    }

    private Session checkAndGetSession(Optional<Session> session) {
        checkIfSessionExist(session);
        //checkIfSessionIsActive(session);
        return session.get();
    }

    private Person checkIfPersonExist(Optional<Person> person) {
        if(person.isEmpty()) {
            throw new BadRequestException("this person is not authorized to grade a group");
        }
        return person.get();
    }

    private void checkIfSessionExist(Optional<Session> session) {
        if (session.isEmpty()) {
            throw new BadRequestException("this session does not exists");
        }
    }

    private void checkIfSessionIsActive(Optional<Session> session) {
        if (!session.get().isActive()) {
            throw new BadRequestException("this session is not active");
        }
    }



}
