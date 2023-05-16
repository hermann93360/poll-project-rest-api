package com.live.pollprojectrestapi.application.dto.request.up.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CreateSessionResponse {

    private final String sessionId;
    public static CreateSessionResponse of(UUID sessionId){
        return CreateSessionResponse.builder()
                .sessionId(sessionId.toString())
                .build();
    }

}
