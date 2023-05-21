package com.live.pollprojectrestapi.domain.service.up;

import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.application.dto.request.up.UpdateGroupRequest;
import com.live.pollprojectrestapi.application.exception.BadRequestException;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.port.persistence.up.GroupPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.PersonPersistence;
import com.live.pollprojectrestapi.domain.port.persistence.up.SessionPersistence;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.GetGroupUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.UpdateGroupInSessionUseCase;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GroupService implements UpdateGroupInSessionUseCase, GetGroupUseCase {

    private SessionPersistence sessionPersistence;
    private GroupPersistence groupPersistence;
    private PersonPersistence personPersistence;

    @Override
    public void updateGroupInSession(UUID groupId, UpdateGroupRequest updateGroupRequest) {
        Group group = checkIfGroupExistAndGet(sessionPersistence.getGroupById(groupId));
        group.setName(updateGroupRequest.getName());
        updateGroupRequest.getPersonsInGroup().forEach(person -> group.getPersonsInGroup().add(getOrCreatePersonByEmail(Person.of(person.getName(), person.getEmail()))));
        group.isSet();

        groupPersistence.updateGroup(group);
    }

    public Person getOrCreatePersonByEmail(Person person) {
        Optional<Person> oPerson = personPersistence.getPerson(Email.of(person.getEmail().getValue()));

        return oPerson.orElseGet(() -> personPersistence.createPerson(person));
    }

    private Group checkIfGroupExistAndGet(Optional<Group> groups) {
        if(groups.isEmpty()) {
            throw new BadRequestException("this group does not exist");
        }
        return groups.get();
    }

    @Override
    public Group getGroup(UUID groupId) {
        return checkIfGroupExistAndGet(sessionPersistence.getGroupById(groupId));
    }
}
