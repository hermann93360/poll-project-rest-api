package com.live.pollprojectrestapi.infra.persistence.repository.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
}
