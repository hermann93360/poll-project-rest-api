package com.live.pollprojectrestapi.application.dto.request.up.response;

import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class GetSessionResponse {
    private final String personId;

    private final String sessionId;
    private final String name;
    private final List<GroupDto> groupsOfSession;

    public static GetSessionResponse of(Session sessionToGet, Person personRequesting) {

        return GetSessionResponse.builder()
                .personId(personRequesting.getPersonId().toString())
                .sessionId(sessionToGet.getSessionId().toString())
                .name(sessionToGet.getName())
                .groupsOfSession(mapGroupDto(sessionToGet.getGroupsOfSession()))
                .build();
    }

    private static List<GroupDto> mapGroupDto(List<Group> groups) {
        return groups.stream()
                .map(GroupDto::fromGroup)
                .collect(Collectors.toList());
    }
}
