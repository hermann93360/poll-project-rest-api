package com.live.pollprojectrestapi.infra.persistence.up;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.model.up.Person;
import com.live.pollprojectrestapi.domain.port.persistence.up.PersonPersistence;
import com.live.pollprojectrestapi.infra.persistence.entity.up.PersonEntity;
import com.live.pollprojectrestapi.infra.persistence.repository.up.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PersonJpa implements PersonPersistence {

    private PersonRepository personRepository;

    @Override
    public Optional<Person> getPerson(Email email) {
        Optional<PersonEntity> byEmail = personRepository.findByEmail(email.getValue());
        return byEmail.map(Person::fromEntity);
    }

    @Override
    public Person createPerson(Person person) {
        PersonEntity personSaved = personRepository.save(PersonEntity.fromModel(person));
        return Person.fromEntity(personSaved);
    }

    @Override
    public Optional<Person> getPersonById(UUID personId) {
        Optional<PersonEntity> byEmail = personRepository.findById(personId);
        return byEmail.map(Person::fromEntity);
    }
}
