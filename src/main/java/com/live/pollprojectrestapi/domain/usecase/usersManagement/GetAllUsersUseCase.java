package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.User;

import java.util.List;

public interface GetAllUsersUseCase {
    List<User> getAllUsers();
}
