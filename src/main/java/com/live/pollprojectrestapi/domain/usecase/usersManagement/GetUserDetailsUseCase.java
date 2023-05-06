package com.live.pollprojectrestapi.domain.usecase.usersManagement;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.User;

public interface GetUserDetailsUseCase {
    User getUserDetails(Email email);
}
