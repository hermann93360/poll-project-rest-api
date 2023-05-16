package com.live.pollprojectrestapi.infra.persistence.entity.up;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "keys")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeysEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long key;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groupId", referencedColumnName = "groupId")
    private GroupEntity associatedGroup;

}
