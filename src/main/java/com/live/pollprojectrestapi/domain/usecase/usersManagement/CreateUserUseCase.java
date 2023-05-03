package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Password;
import com.live.pollprojectrestapi.domain.model.User;

public interface CreateUserUseCase {
    void createUser(User user, Password password);
}
