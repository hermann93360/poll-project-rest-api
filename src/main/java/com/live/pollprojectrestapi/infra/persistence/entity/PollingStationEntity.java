package com.live.pollprojectrestapi.infra.persistence.entity;

import com.live.pollprojectrestapi.domain.model.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity administrator;

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

    @OneToMany(mappedBy = "pollingStation")
    private List<SubjectEntity> subjects;

    @ManyToMany
    @JoinTable(name = "pollingStation_users",
            joinColumns = @JoinColumn(name = "pollingStationId"),
            inverseJoinColumns = @JoinColumn(name = "userParticipantId"))
    private List<UserEntity> usersParticipants;

    public static PollingStationEntity fromModel(PollingStation pollingStation) {
        return  PollingStationEntity.builder()
                .pollingStationId(pollingStation.getPollingStationId())
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
                .subjects(pollingStation.getSubjects() == null ? new ArrayList<>() : getSubjectsEntityFromModel(pollingStation.getSubjects()))
                .usersParticipants(pollingStation.getUsersParticipants() == null ? new ArrayList<>() : getUsersParticipantsEntityFromModel(pollingStation.getUsersParticipants()))
                .build();
    }

    private static List<SubjectEntity> getSubjectsEntityFromModel(List<Subject> subjects){
        return subjects.stream()
                .map(SubjectEntity::fromModel)
                .collect(Collectors.toList());
    }

    private static List<UserEntity> getUsersParticipantsEntityFromModel(List<User> users){
        return users.stream()
                .map(UserEntity::fromModel)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "PollingStationEntity{" +
                "pollingStationId=" + pollingStationId +
                ", administratorId=" + administratorId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", typeNotation='" + typeNotation + '\'' +
                ", notationVisible=" + notationVisible +
                ", userLimit=" + userLimit +
                ", scope='" + scope + '\'' +
                ", password='" + password + '\'' +
                ", pollType='" + pollType + '\'' +
                ", startPoll=" + startPoll +
                ", endPoll=" + endPoll +
                '}';
    }
}
