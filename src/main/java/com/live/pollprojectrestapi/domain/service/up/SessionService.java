package com.live.pollprojectrestapi.domain.service.up;

import com.live.pollprojectrestapi.application.dto.request.up.*;
import com.live.pollprojectrestapi.application.dto.request.up.response.GetResultResponse;
import com.live.pollprojectrestapi.application.dto.request.up.response.ResultDto;
import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.*;
import com.live.pollprojectrestapi.domain.port.persistence.up.GroupPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.PersonPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.SessionPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService implements
        CreateSessionUseCase,
        GetSessionUseCase,
        GetOrCreatePersonByEmailUseCase,
        AddGroupInSessionUseCase,
        GradeSessionUseCase,
        CreateResultUseCase,
        GetResultUseCase,
        CheckIfSessionExistUseCase,
        GetPersonByEmail {

    private SessionPersistence sessionPersistence;
    private PersonPersistence personPersistence;
    private GroupPersistence groupPersistence;
    private SendMailForCreateGroupUseCase sendMailForCreateGroupUseCase;

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
    public Session getSession(String code, ManagePersonRequest managePersonRequest) {
        Person personToGet = getPersonByEmail(managePersonRequest);

        Optional<Session> getSession = sessionPersistence.getSessionByCode(code);
        Session session = checkAndGetSession(getSession);

        if (session.personHasAlreadyGrade(personToGet)) {
            throw new BadRequestException("This person has already grade");
        }
        if (session.isPrivate()) {
            checkIfPersonIsAuthorizedToJoinSession(session, personToGet);
        }

        session.setCountParticipants(session.getCountParticipants() + 1);
        sessionPersistence.createSession(session);

        session.setGroupsOfSession(session.getGroupsOfSession().stream().filter(group -> group.personInGroup(personToGet)).collect(Collectors.toList()));


        return session;
    }

    @Override
    public Person getPersonByEmail(ManagePersonRequest managePersonRequest) {
        Optional<Person> person = personPersistence.getPerson(Email.of(managePersonRequest.getEmailOfRequesting()));

        if (person.isEmpty()) {
            throw new BadRequestException("this person does not exist");
        }

        return person.get();
    }

    @Override
    public Person getOrCreatePersonByEmail(ManagePersonRequest managePersonRequest) {
        Person personToGet = Person.fromRequest(managePersonRequest);

        Optional<Person> person = personPersistence.getPerson(Email.of(managePersonRequest.getEmailOfRequesting()));

        return person.orElseGet(() -> personPersistence.createPerson(personToGet));
    }

    @Override
    public UUID addGroupInSessionUseCase(UUID sessionId, Person firstPersonIdGroup) {

        checkIfSessionExist(sessionPersistence.getSession(sessionId));

        Group groupToAdd = Group.emptyGroup();

        return sessionPersistence.addGroupInSession(sessionId, groupToAdd, firstPersonIdGroup);

    }

    @Override
    public void gradeSessionUseCase(UUID sessionId, AddGradesInSessionRequest addGradesInSessionRequest) {
        Session session = checkAndGetSession(sessionPersistence.getSession(sessionId));

        if (session.personAlreadyGraded(UUID.fromString(addGradesInSessionRequest.getPersonId()))) {
            throw new BadRequestException("this person has already graded this session");
        }

        addGradesInSessionRequest.getGrades().forEach(
                grade -> {
                    checkIfGroupExistAndGet(sessionPersistence.getGroupById(UUID.fromString(grade.getGroupId())));

                    Person person = checkIfPersonExist(personPersistence.getPersonById(UUID.fromString(grade.getPersonId())));

                    Grade gradeToSave = Grade.of(grade.getGrade());

                    sessionPersistence.addGradeInSession(UUID.fromString(grade.getGroupId()), gradeToSave, person);
                });

    }

    @Override
    public Session createResultAndGet(UUID sessionId) {
        Session session = checkAndGetSession(sessionPersistence.getSession(sessionId));

        if (session.allGroupHaveSameGradeCount()) {
            session.getGroupsOfSession().forEach(Group::getCalcAverage);
        }

        return session;
    }

    @Override
    public GetResultResponse getResult(String code) {
        UUID sessionId = sessionPersistence.getSessionByCode(code).get().getSessionId();

        Session session = createResultAndGet(sessionId);

        if (!session.canGetResult()) {
            throw new BadRequestException("Must to every grade this session");
        }

        List<ResultDto> results = new ArrayList<>();
        session.getGroupsOfSession().forEach(group -> results.add(group.buildResult()));

        return GetResultResponse.of(results);
    }

    private Group checkIfGroupExistAndGet(Optional<Group> groups) {
        if (groups.isEmpty()) {
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
        checkIfSessionIsActive(session);
        return session.get();
    }

    private Person checkIfPersonExist(Optional<Person> person) {
        if (person.isEmpty()) {
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
        if (!session.get().isActivate()) {
            throw new BadRequestException("this session is not active");
        }
    }


    @Override
    public boolean check(String sessionId) {
        Optional<Session> getSession = sessionPersistence.getSessionByCode(sessionId);
        return getSession.isPresent() && getSession.get().isActivate();
    }
}
