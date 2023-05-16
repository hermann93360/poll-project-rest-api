package com.live.pollprojectrestapi.infra.persistence.repository.up;

import com.live.pollprojectrestapi.infra.persistence.entity.up.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {
}
