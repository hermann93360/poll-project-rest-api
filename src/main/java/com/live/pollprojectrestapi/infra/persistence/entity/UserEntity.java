package com.live.pollprojectrestapi.infra.persistence.entity;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "administrator")
    private List<PollingStationEntity> pollingStations;

    private String username;

    private String nickname;

    private String email;

    public static UserEntity fromModel(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .pollingStations(getPollingStationEntityFromModel(user.getPollingStations()))
                .username(user.getUsername())
                .email(user.getEmail().getValue())
                .nickname(user.getNickname())
                .build();
    }

    private static List<PollingStationEntity> getPollingStationEntityFromModel(List<PollingStation> pollingStations){
        return pollingStations.stream()
                .map(PollingStationEntity::fromModel)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
