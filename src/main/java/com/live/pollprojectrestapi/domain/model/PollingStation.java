package com.live.pollprojectrestapi.domain.model;

import com.live.pollprojectrestapi.application.dto.request.CreatePollingStationRequest;
import com.live.pollprojectrestapi.domain.PollingStationDescription;
import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.swing.plaf.basic.BasicDirectoryModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class PollingStation {
    private UUID pollingStationId;
    private PollingStationDescription pollingStationDescription;
    private PollingStationSettings pollingStationSettings;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<Subject> subjects;

    public static PollingStation fromEntity(PollingStationEntity pollingStationEntity) {
        PollingStationDescription psd = getPollingStationDescription(pollingStationEntity);
        PollingStationSettings pss = getPollingStationSettings(pollingStationEntity);


        return  PollingStation.builder()
                .pollingStationId(pollingStationEntity.getPollingStationId())
                .pollingStationDescription(psd)
                .pollingStationSettings(pss)
                .start(pollingStationEntity.getStartPoll())
                .end(pollingStationEntity.getEndPoll())
                .subjects(getSubjectsFromEntitys(pollingStationEntity.getSubjects()))
                .build();
    }

    private static List<Subject> getSubjectsFromEntitys(List<SubjectEntity> subjects) {
        return subjects.stream()
                .map(Subject::fromEntity)
                .collect(Collectors.toList());
    }

    private static PollingStationSettings getPollingStationSettings(PollingStationEntity pollingStationEntity) {
        return PollingStationSettings.builder()
                .typeNotation(TypeNotation.valueOf(pollingStationEntity.getTypeNotation()))
                .notationVisible(pollingStationEntity.isNotationVisible())
                .userLimit(pollingStationEntity.getUserLimit())
                .scope(Scope.valueOf(pollingStationEntity.getScope()))
                .password(pollingStationEntity.getPassword())
                .pollType(PollType.valueOf(pollingStationEntity.getPollType()))
                .build();
    }

    private static PollingStationDescription getPollingStationDescription(PollingStationEntity pollingStationEntity) {
        return PollingStationDescription.builder()
                .name(pollingStationEntity.getName())
                .category(pollingStationEntity.getCategory())
                .description(pollingStationEntity.getDescription())
                .keywords(pollingStationEntity.getKeywords())
                .build()
                .check();
    }





















    public static PollingStation fromRequest(CreatePollingStationRequest createPollingStationRequest){
        PollingStationDescription psd = getPollingStationDescription(createPollingStationRequest);
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
                .typeNotation(TypeNotation.valueOf(createPollingStationRequest.getTypeNotation()))
                .notationVisible(createPollingStationRequest.isNotationVisible())
                .userLimit(createPollingStationRequest.getUserLimit())
                .scope(Scope.valueOf(createPollingStationRequest.getScope()))
                .password(createPollingStationRequest.getPassword())
                .pollType(PollType.valueOf(createPollingStationRequest.getPollType()))
                .build();
    }

    private static PollingStationDescription getPollingStationDescription(CreatePollingStationRequest createPollingStationRequest) {
        return PollingStationDescription.builder()
                .name(createPollingStationRequest.getName())
                .category(createPollingStationRequest.getCategory())
                .description(createPollingStationRequest.getDescription())
                .keywords(createPollingStationRequest.getKeywords())
                .build()
                .check();
    }

    @Override
    public String toString() {
        return "PollingStation{" +
                "pollingStationId=" + pollingStationId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
