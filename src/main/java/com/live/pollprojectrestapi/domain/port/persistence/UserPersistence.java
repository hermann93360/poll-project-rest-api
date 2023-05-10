package com.live.pollprojectrestapi.domain.port.persistence;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserPersistence {
    void createUser(User user);

    User findByUserId(UserId userId);

    Optional<User> findByEmail(Email email);

    List<User> findAll();
}
