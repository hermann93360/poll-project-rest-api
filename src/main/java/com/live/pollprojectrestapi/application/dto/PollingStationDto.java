package com.live.pollprojectrestapi.application.dto;

import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class PollingStationDto {
    private UUID pollingStationId;
    private UUID administratorId;
    private String nameOfAdministrator;
    private String name;
    private String category;
    private String description;
    private String keywords;
    private String typeNotation;
    private boolean notationVisible;
    private Integer userLimit;
    private String scope;
    private String password;
    private String pollType;
    private LocalDateTime startPoll;
    private LocalDateTime endPoll;

    public static PollingStationDto fromModel(PollingStation pollingStation) {
        return  PollingStationDto.builder()
                .pollingStationId(pollingStation.getPollingStationId())
                .nameOfAdministrator(pollingStation.getPollingStationDescription().getAdministrator().getUsername())
                .administratorId(pollingStation.getPollingStationDescription().getAdministrator().getId())
                .name(pollingStation.getPollingStationDescription().getName())
                .category(pollingStation.getPollingStationDescription().getCategory())
                .description(pollingStation.getPollingStationDescription().getDescription())
                .keywords(pollingStation.getPollingStationDescription().getKeywords())
                .password(pollingStation.getPollingStationSettings().getPassword())
                .typeNotation(pollingStation.getPollingStationSettings().getTypeNotation().name())
                .notationVisible(pollingStation.getPollingStationSettings().isNotationVisible())
                .userLimit(pollingStation.getPollingStationSettings().getUserLimit())
                .scope(pollingStation.getPollingStationSettings().getScope().name())
                .pollType(pollingStation.getPollingStationSettings().getPollType().name())
                .startPoll(pollingStation.getStart())
                .endPoll(pollingStation.getEnd())
                .build();
    }
}
