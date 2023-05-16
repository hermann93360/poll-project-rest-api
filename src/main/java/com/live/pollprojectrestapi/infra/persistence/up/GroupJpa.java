package com.live.pollprojectrestapi.infra.persistence.up;

import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.port.persistence.up.GroupPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.up.SessionEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.up.GroupRepository;
import com.live.pollprojectrestapi.infra.persistence.repository.up.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GroupJpa implements GroupPersistence {

    private GroupRepository groupRepository;
    private PersonRepository personRepository;

    @Override
    public void updateGroup(Group group) {
        GroupEntity groupToUpdate = GroupEntity.fromModel(group);
        SessionEntity groupForGetSession = groupRepository.findById(group.getGroupId()).get().getAssociatedSession();

        groupToUpdate.setAssociatedSession(groupForGetSession);
        groupToUpdate.getPersonsInGroup().forEach(person -> person.setAssociatedGroup(groupToUpdate));

        groupRepository.save(groupToUpdate);
        personRepository.saveAll(groupToUpdate.getPersonsInGroup());

    }
}
