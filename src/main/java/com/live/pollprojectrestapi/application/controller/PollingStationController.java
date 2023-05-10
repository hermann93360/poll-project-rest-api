package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.application.dto.PollingStationDto;
import com.live.pollprojectrestapi.application.dto.request.AddSubjectToPollingStationRequest;
import com.live.pollprojectrestapi.application.dto.request.CreatePollingStationRequest;
import com.live.pollprojectrestapi.application.dto.request.CreateUserRequest;
import com.live.pollprojectrestapi.application.dto.response.GetAllPollingStationResponse;
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
    private GetAdministratorToPollingStation getAdministratorToPollingStation;
    private GetPollingStationUserUseCase getPollingStationUserUseCase;

    @PostMapping("/polling-station/create")
    public void createPollingStation(@RequestBody CreatePollingStationRequest createPollingStationRequest) {
        Email emailOwner = AuthUtils.getUserEmailPrincipal();
        PollingStation ps = PollingStation.fromRequest(createPollingStationRequest);

        createPollingStationUseCase.createPollingStationUseCase(ps, emailOwner);
    }

    @PostMapping("/polling-station/subject/add")
    public void addSubjectToPollingStation(@RequestBody AddSubjectToPollingStationRequest addSubjectToPollingStationRequest) {
        Subject subjectToAdd = Subject.fromRequest(addSubjectToPollingStationRequest);
        UUID pollingStationId = addSubjectToPollingStationRequest.getPollingStationId();

        addSubjectToPollingStationUseCase.addSubjectToPollingStationUseCase(subjectToAdd, pollingStationId);
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

}
