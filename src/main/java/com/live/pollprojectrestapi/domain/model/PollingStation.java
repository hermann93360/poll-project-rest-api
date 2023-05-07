package com.live.pollprojectrestapi.domain.model;

import com.live.pollprojectrestapi.application.dto.request.CreatePollingStationRequest;
import com.live.pollprojectrestapi.domain.PollingStationDescription;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class PollingStation {
    private UUID pollingStationId;
    private PollingStationDescription pollingStationDescription;
    private PollingStationSettings pollingStationSettings;
    private LocalDateTime start;
    private LocalDateTime end;

    public static PollingStation fromEntity(PollingStationEntity pollingStationEntity, User user) {
        PollingStationDescription psd = getPollingStationDescription(pollingStationEntity, user);
        PollingStationSettings pss = getPollingStationSettings(pollingStationEntity);


        return  PollingStation.builder()
                .pollingStationId(pollingStationEntity.getPollingStationId())
                .pollingStationDescription(psd)
                .pollingStationSettings(pss)
                .start(pollingStationEntity.getStartPoll())
                .end(pollingStationEntity.getEndPoll())
                .build();
    }

    private static PollingStationSettings getPollingStationSettings(PollingStationEntity pollingStationEntity) {
        return PollingStationSettings.builder()
                .typeNotation(TypeNotation.valueOf(pollingStationEntity.getPollType()))
                .notationVisible(pollingStationEntity.isNotationVisible())
                .userLimit(pollingStationEntity.getUserLimit())
                .scope(Scope.valueOf(pollingStationEntity.getScope()))
                .password(pollingStationEntity.getPassword())
                .pollType(PollType.valueOf(pollingStationEntity.getPollType()))
                .build();
    }

    private static PollingStationDescription getPollingStationDescription(PollingStationEntity pollingStationEntity, User user) {
        return PollingStationDescription.builder()
                .administrator(user)
                .name(pollingStationEntity.getName())
                .category(pollingStationEntity.getCategory())
                .description(pollingStationEntity.getDescription())
                .keywords(pollingStationEntity.getKeywords())
                .build()
                .check();
    }

    public static PollingStation fromRequest(CreatePollingStationRequest createPollingStationRequest, User administrator){
        PollingStationDescription psd = getPollingStationDescription(createPollingStationRequest, administrator);
        PollingStationSettings pss = getPollingStationSettings(createPollingStationRequest);


        return  PollingStation.builder()
                .pollingStationDescription(psd)
                .pollingStationSettings(pss)
                .start(createPollingStationRequest.getStart())
                .end(createPollingStationRequest.getEnd())
                .build();
    }

    private static PollingStationSettings getPollingStationSettings(CreatePollingStationRequest createPollingStationRequest) {
        return PollingStationSettings.builder()
                .typeNotation(TypeNotation.valueOf(createPollingStationRequest.getPollType()))
                .notationVisible(createPollingStationRequest.isNotationVisible())
                .userLimit(createPollingStationRequest.getUserLimit())
                .scope(Scope.valueOf(createPollingStationRequest.getScope()))
                .password(createPollingStationRequest.getPassword())
                .pollType(PollType.valueOf(createPollingStationRequest.getPollType()))
                .build();
    }

    private static PollingStationDescription getPollingStationDescription(CreatePollingStationRequest createPollingStationRequest, User administrator) {
        return PollingStationDescription.builder()
                .administrator(administrator)
                .name(createPollingStationRequest.getName())
                .category(createPollingStationRequest.getCategory())
                .description(createPollingStationRequest.getDescription())
                .keywords(createPollingStationRequest.getKeywords())
                .build()
                .check();
    }
}
