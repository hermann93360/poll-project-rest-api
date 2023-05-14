package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.application.dto.PollingStationDto;
import com.live.pollprojectrestapi.application.dto.UserDetailsDto;
import com.live.pollprojectrestapi.application.dto.notification.DemandJoinStationNotification;
import com.live.pollprojectrestapi.application.dto.request.AddSubjectToPollingStationRequest;
import com.live.pollprojectrestapi.application.dto.request.CreatePollingStationRequest;
import com.live.pollprojectrestapi.application.dto.request.CreateUserRequest;
import com.live.pollprojectrestapi.application.dto.response.GetAllPollingStationResponse;
import com.live.pollprojectrestapi.application.dto.response.GetDemandNotificationToUserResponse;
import com.live.pollprojectrestapi.application.dto.response.GetPollingStationResponse;
import com.live.pollprojectrestapi.application.socket.SocketNotification;
import com.live.pollprojectrestapi.domain.model.*;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@AllArgsConstructor
public class PollingStationController {

    private GetUserDetailsUseCase getUserDetailsUseCase;

    private CreatePollingStationUseCase createPollingStationUseCase;
    private GetAllPollingStationUseCase getAllPollingStationUseCase;
    private AddSubjectToPollingStationUseCase addSubjectToPollingStationUseCase;
    private AddUserInPollingStationUseCase addUserInPollingStationUseCase;
    private GetAdministratorToPollingStation getAdministratorToPollingStation;

    private GetPollingStationUserUseCase getPollingStationUserUseCase;
    private GetPollingStationById getPollingStationById;

    private CreateDemandForJoinStationUseCase createDemandForJoinStationUseCase;
    private GetDemandNotificationToUserUseCase getDemandNotificationToUserUseCase;

    private AcceptDemandForJoinStationUseCase acceptDemandForJoinStationUseCase;
    private RejectDemandForJoinStationUseCase rejectDemandForJoinStationUseCase;

    private SocketNotification socketNotification;


    @PostMapping("/polling-station/create")
    public void createPollingStation(@RequestBody CreatePollingStationRequest createPollingStationRequest) {
        Email emailOwner = AuthUtils.getUserEmailPrincipal();
        PollingStation ps = PollingStation.fromRequest(createPollingStationRequest);

        createPollingStationUseCase.createPollingStationUseCase(ps, emailOwner);
    }

    @PostMapping("/polling-station/{pollingStationId}/subjects/add")
    public void addSubjectToPollingStation(
            @PathVariable UUID pollingStationId,
            @RequestBody AddSubjectToPollingStationRequest addSubjectToPollingStationRequest) {
        Subject subjectToAdd = Subject.fromRequest(addSubjectToPollingStationRequest);

        addSubjectToPollingStationUseCase.addSubjectToPollingStationUseCase(subjectToAdd, pollingStationId);

        socketNotification.send("/socket/station/subjects/"+pollingStationId, subjectToAdd);
        System.out.println("/socket/station/subjects/"+pollingStationId);
    }



    @GetMapping("/polling-station/all")
    public GetAllPollingStationResponse getAllPollingStation() {
        List<PollingStation> pollingStationLists = getAllPollingStationUseCase.getAll();
        List<User> administrators = pollingStationLists.stream()
                .map(x -> getAdministratorToPollingStation.getAdministratorToPollingStation(x.getPollingStationId()))
                .collect(Collectors.toList());

        return GetAllPollingStationResponse.of(pollingStationLists, administrators);
    }

    @GetMapping("/polling-station")
    public GetAllPollingStationResponse getPollingStationToUser() {
        List<PollingStation> pollingStationLists = getPollingStationUserUseCase.getPollingStationUserUseCase(AuthUtils.getUserEmailPrincipal());
        List<User> administrators = pollingStationLists.stream()
                .map(x -> getAdministratorToPollingStation.getAdministratorToPollingStation(x.getPollingStationId()))
                .collect(Collectors.toList());

        return GetAllPollingStationResponse.of(pollingStationLists, administrators);
    }

    @GetMapping("/polling-station/{pollingStationId}")
    public GetPollingStationResponse getPollingStationById(@PathVariable UUID pollingStationId) {
        PollingStation ps = getPollingStationById.getPollingStationById(pollingStationId);
        User administrator = getAdministratorToPollingStation.getAdministratorToPollingStation(pollingStationId);

        return GetPollingStationResponse.of(ps, administrator);
    }

    @PostMapping("/polling-station/{pollingStationId}/users/{emailUserToJoin}/demand")
    public void addDemandToJoinPollingStation(
            @PathVariable UUID pollingStationId,
            @PathVariable String emailUserToJoin
    ) {
        Email assignedToEmail = Email.of(emailUserToJoin);
        User assignedTo = getUserDetailsUseCase.getUserDetails(assignedToEmail);

        Email requestingUserEmail = AuthUtils.getUserEmailPrincipal();
        User requestingUser = getUserDetailsUseCase.getUserDetails(requestingUserEmail);

        Demand demand = createDemandForJoinStationUseCase.createDemandForJoinStation(pollingStationId, requestingUser, assignedTo);

        DemandJoinStationNotification notification = DemandJoinStationNotification.of(demand);
        socketNotification.send("/socket/notify/"+demand.getAssignedTo().getId(), notification);

    }

    @PostMapping("/polling-station/{pollingStationId}/users/add")
    public void addSubjectToPollingStation(
            @PathVariable UUID pollingStationId) {

        User user = getUserDetailsUseCase.getUserDetails(AuthUtils.getUserEmailPrincipal());
        addUserInPollingStationUseCase.addUserInPollingStation(user, pollingStationId);
    }

    @GetMapping("/polling-station/notify/demand")
    public GetDemandNotificationToUserResponse getDemandNotificationToUser() {
        List<Demand> demand = getDemandNotificationToUserUseCase.getDemandNotificationToUser(AuthUtils.getUserEmailPrincipal());
        return GetDemandNotificationToUserResponse.of(demand);
    }

    @PostMapping("/polling-station/demand/{demandId}/accept")
    public void acceptDemandToUser(@PathVariable UUID demandId) {
        Demand demand = acceptDemandForJoinStationUseCase.acceptDemandForJoinStation(demandId);

        socketNotification.send("/socket/station/users/"+demand.getSubjectRequesting().getPollingStationId(), UserDetailsDto.fromModel(demand.getAssignedTo()));
    }

    @PostMapping("/polling-station/demand/{demandId}/reject")
    public void rejectDemandToUser(@PathVariable UUID demandId) {
        rejectDemandForJoinStationUseCase.rejectDemandForJoinStation(demandId);
    }



}
