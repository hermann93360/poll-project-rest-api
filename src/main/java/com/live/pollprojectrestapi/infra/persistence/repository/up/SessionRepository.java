package com.live.pollprojectrestapi.infra.persistence.repository.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
}
