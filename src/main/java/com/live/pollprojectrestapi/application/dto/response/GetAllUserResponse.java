package com.live.pollprojectrestapi.application.dto.response;

import com.live.pollprojectrestapi.application.dto.UserDetailsDto;
import com.live.pollprojectrestapi.application.dto.UserDto;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllUserResponse {

    private final List<UserDetailsDto> users;
    public static GetAllUserResponse of(List<User> users) {
        return new GetAllUserResponse(users);
    }

    public GetAllUserResponse(List<User> users) {
        this.users = users.stream()
                .map(UserDetailsDto::fromModel)
                .collect(Collectors.toList());
    }
}
