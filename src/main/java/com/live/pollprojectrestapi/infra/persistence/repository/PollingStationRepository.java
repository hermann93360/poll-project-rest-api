package com.live.pollprojectrestapi.infra.persistence.repository;

import com.live.pollprojectrestapi.infra.persistence.entity.PollingStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollingStationRepository extends JpaRepository<PollingStationEntity, UUID> {
}
