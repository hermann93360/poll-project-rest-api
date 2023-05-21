package com.live.pollprojectrestapi.application.controller.up;

import com.live.pollprojectrestapi.application.controller.AuthUtils;
import com.live.pollprojectrestapi.application.dto.request.up.AddGradesInSessionRequest;
import com.live.pollprojectrestapi.application.dto.request.up.CreateSessionRequest;
import com.live.pollprojectrestapi.application.dto.request.up.ManagePersonRequest;
import com.live.pollprojectrestapi.application.dto.request.up.UpdateGroupRequest;
import com.live.pollprojectrestapi.application.dto.request.up.response.CreateSessionResponse;
import com.live.pollprojectrestapi.application.dto.request.up.response.GetGroupResponse;
import com.live.pollprojectrestapi.application.dto.request.up.response.GetResultResponse;
import com.live.pollprojectrestapi.application.dto.request.up.response.GetSessionResponse;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.model.up.Session;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class SessionController {

    private CreateSessionUseCase createSessionUseCase;
    private GetSessionUseCase getSessionUseCase;
    private GetOrCreatePersonByEmailUseCase getOrCreatePersonByEmailUseCase;
    private AddGroupInSessionUseCase addGroupInSessionUseCase;
    private UpdateGroupInSessionUseCase updateGroupInSessionUseCase;
    private GradeSessionUseCase gradeSessionUseCase;
    private GetResultUseCase getResultUseCase;
    private SendMailForCreateGroupUseCase sendMailForCreateGroupUseCase;
    private GetGroupUseCase getGroupUseCase;
    private CheckIfSessionExistUseCase checkIfSessionExistUseCase;
    private GetPersonByEmail getPersonByEmail;

    @PostMapping("/sessions/create")
    public CreateSessionResponse createSession(@RequestBody CreateSessionRequest createSessionRequest) {
        Email emailOfUserLogged = AuthUtils.getUserEmailPrincipal();
        UUID sessionId = createSessionUseCase.createSession(emailOfUserLogged, createSessionRequest);
        return CreateSessionResponse.of(sessionId);
    }

    @PostMapping("/sessions/{code}")
    public GetSessionResponse getSession(
            @PathVariable String code,
            @RequestBody ManagePersonRequest managePersonRequest) {

        Session sessionToGet = getSessionUseCase.getSession(code, managePersonRequest);
        Person personRequesting = getPersonByEmail.getPersonByEmail(managePersonRequest);
        return GetSessionResponse.of(sessionToGet, personRequesting);

    }

    @PostMapping("/sessions/{sessionId}/group/add")
    public void addGroup(
            @PathVariable String sessionId,
            @RequestBody ManagePersonRequest managePersonRequest) {

        Person firstPersonInGroup = getOrCreatePersonByEmailUseCase.getOrCreatePersonByEmail(managePersonRequest);
        UUID groupId = addGroupInSessionUseCase.addGroupInSessionUseCase(UUID.fromString(sessionId), firstPersonInGroup);
        sendMailForCreateGroupUseCase.sendMailForCreateGroup(firstPersonInGroup, groupId, UUID.fromString(sessionId));
    }

    @PatchMapping("/group/{groupId}/update")
    public void updateGroup(
            @PathVariable String groupId,
            @RequestBody UpdateGroupRequest updateGroupRequest) {
        updateGroupInSessionUseCase.updateGroupInSession(UUID.fromString(groupId), updateGroupRequest);
    }

    @PostMapping("/sessions/{sessionId}/grades/add")
    public void gradeGroup(
            @PathVariable String sessionId,
            @RequestBody AddGradesInSessionRequest addGradesInSessionRequest) {

        gradeSessionUseCase.gradeSessionUseCase(UUID.fromString(sessionId), addGradesInSessionRequest);
    }

    @GetMapping("/sessions/{code}/results")
    public GetResultResponse getResults(
            @PathVariable String code) {
        return getResultUseCase.getResult(code);
    }
    @GetMapping("/sessions/check/{sessionId}")
    public boolean checkIfSessionExist(
            @PathVariable String sessionId) {
        return checkIfSessionExistUseCase.check(sessionId);
    }


    @GetMapping("/groups/{groupId}")
    public GetGroupResponse getgroupById(
            @PathVariable String groupId) {

        return GetGroupResponse.of(getGroupUseCase.getGroup(UUID.fromString(groupId)));
    }

}
