package com.live.pollprojectrestapi.application.dto.request.up.response;

import com.live.pollprojectrestapi.domain.model.up.Grade;
import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class GetGroupResponse {
    private boolean configured;
    public static GetGroupResponse of(Group group) {
        return new GetGroupResponse(group.isConfigured());
    }
}
