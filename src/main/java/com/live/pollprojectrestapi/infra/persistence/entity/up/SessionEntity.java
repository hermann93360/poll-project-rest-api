package com.live.pollprojectrestapi.infra.persistence.entity.up;

import com.live.pollprojectrestapi.domain.model.up.Group;
import com.live.pollprojectrestapi.domain.model.up.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "session")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sessionId;

    private String name;

    @OneToMany(mappedBy = "associatedSession")
    private List<GroupEntity> groupsOfSession;

    private String description;

    private String keywords;

    private boolean active;

    private Integer countGroup;

    private Integer countParticipants;

    private String scope;

    public static SessionEntity fromModel(Session session) {
        return SessionEntity.builder()
                .sessionId(session.getSessionId())
                .name(session.getName())
                .groupsOfSession(mapGroupModels(session.getGroupsOfSession()))
                .description(session.getDescription())
                .keywords(session.getKeywords())
                .active(session.isActive())
                .countGroup(session.getCountGroup())
                .countParticipants(session.getCountParticipants())
                .scope(session.getScope())
                .build();
    }

    private static List<GroupEntity> mapGroupModels(List<Group> groups) {
        return groups.stream()
                .map(GroupEntity::fromModel)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "sessionId=" + sessionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", active=" + active +
                ", countGroup=" + countGroup +
                ", countParticipants=" + countParticipants +
                ", scope='" + scope + '\'' +
                '}';
    }
}

