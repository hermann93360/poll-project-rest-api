package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;

public interface UpdateUserUseCase {

    void updateUser(UserId userId, User user);
}
