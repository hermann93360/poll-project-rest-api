package com.live.pollprojectrestapi.application.controller;

import com.live.pollprojectrestapi.application.dto.request.CreatePollingStationRequest;
import com.live.pollprojectrestapi.application.dto.request.CreateUserRequest;
import com.live.pollprojectrestapi.application.dto.response.GetAllPollingStationResponse;
import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.Password;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.CreatePollingStationUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.GetAllPollingStationUseCase;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.GetUserDetailsUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@AllArgsConstructor
public class PollingStationController {

    private GetUserDetailsUseCase getUserDetailsUseCase;
    private CreatePollingStationUseCase createPollingStationUseCase;
    private GetAllPollingStationUseCase getAllPollingStationUseCase;

    @PostMapping("/pollingStation/create")
    public void createPollingStation(@RequestBody CreatePollingStationRequest createPollingStationRequest) {
        User administrator = getUserDetailsUseCase.getUserDetails(AuthUtils.getUserEmailPrincipal());
        PollingStation ps = PollingStation.fromRequest(createPollingStationRequest, administrator);

        createPollingStationUseCase.createPollingStationUseCase(ps);
    }

    @GetMapping("/pollingStation")
    public GetAllPollingStationResponse getAllPollingStation() {
        return GetAllPollingStationResponse.of(getAllPollingStationUseCase.getAll());
    }

}
