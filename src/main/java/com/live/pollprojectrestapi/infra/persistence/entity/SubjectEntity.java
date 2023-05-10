package com.live.pollprojectrestapi.infra.persistence.entity;

import com.live.pollprojectrestapi.domain.model.PollingStation;
import com.live.pollprojectrestapi.domain.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subject")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pollingStationId")
    private PollingStationEntity pollingStation;

    private String name;
    private String pathImg;
    private String description;

    public static SubjectEntity fromModel(Subject subject) {
        return SubjectEntity.builder()
                .name(subject.getName())
                .pathImg(subject.getPathImg())
                .description(subject.getDescription())
                .build();
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pathImg='" + pathImg + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
