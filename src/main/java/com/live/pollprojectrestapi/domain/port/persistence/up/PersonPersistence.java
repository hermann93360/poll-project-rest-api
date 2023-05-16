package com.live.pollprojectrestapi.domain.port.persistence.up;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonPersistence {
    Optional<Person> getPerson(Email email);
    Person createPerson(Person person);

    Optional<Person> getPersonById(UUID personId);
}
