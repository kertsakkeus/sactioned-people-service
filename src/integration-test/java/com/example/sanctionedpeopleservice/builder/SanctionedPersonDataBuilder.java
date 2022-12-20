package com.example.sanctionedpeopleservice.builder;

import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import org.springframework.stereotype.Service;

@Service
public class SanctionedPersonDataBuilder {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public SanctionedPersonDataBuilder(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public SanctionedPerson createSanctionedPerson() {
    return sanctionedPersonRepository.save(SanctionedPerson.builder()
                                               .personName("TEST PERSON")
                                               .build());
  }
}