package com.live.pollprojectrestapi.infra.persistence.repository.up;

import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
    Optional<PersonEntity> findByEmail(String email);
}
