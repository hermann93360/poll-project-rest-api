package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.domain.model.up.Group;

import java.util.UUID;

public interface GetGroupUseCase {

    Group getGroup(UUID groupId);
}
