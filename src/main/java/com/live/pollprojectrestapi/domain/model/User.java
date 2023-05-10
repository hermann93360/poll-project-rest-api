package com.live.pollprojectrestapi.domain.model;

import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import com.live.pollprojectrestapi.infra.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class User {
    private UUID id;
    private String username;
    private String nickname;
    private Email email;
    private List<PollingStation> pollingStations;

    public static User fromEntity(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(Email.of(user.getEmail()))
                .pollingStations(getPollingStationsFromEntity(user.getPollingStations()))
                .build();
    }

    private static List<PollingStation> getPollingStationsFromEntity(List<PollingStationEntity> pollingStations) {
        return pollingStations.stream()
                .map(PollingStation::fromEntity)
                .collect(Collectors.toList());
    }

    public void replace(User user) {
        id = user.id;
        username = user.getUsername();
        nickname = user.getNickname();
        email = user.getEmail();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email=" + email +
                '}';
    }
}
