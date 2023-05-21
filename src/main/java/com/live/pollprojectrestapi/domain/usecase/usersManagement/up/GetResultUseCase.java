package com.live.pollprojectrestapi.domain.usecase.usersManagement.up;

import com.live.pollprojectrestapi.application.dto.request.up.response.GetResultResponse;
import com.live.pollprojectrestapi.application.dto.request.up.response.GetSessionResponse;
import com.live.pollprojectrestapi.domain.model.up.ResultSession;

import java.util.UUID;

public interface GetResultUseCase {
    GetResultResponse getResult(String code);
}
