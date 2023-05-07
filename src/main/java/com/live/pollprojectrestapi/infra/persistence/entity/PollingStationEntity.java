package com.live.pollprojectrestapi.infra.persistence.entity;

import com.live.pollprojectrestapi.domain.model.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PollingStation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PollingStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pollingStationId;
    private UUID administratorId;
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

    public static PollingStationEntity fromModel(PollingStation pollingStation) {
        return  PollingStationEntity.builder()
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
