package com.example.sanctionedpeopleservice;

import com.example.sanctionedpeopleservice.builder.SanctionedPersonDataBuilder;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.springframework.stereotype.Service;

@Service
public class TestDataBuilder {
  private final GeneratedPerson generatedPerson = new GeneratedPerson();

  private final SanctionedPersonDataBuilder sanctionedPersonDataBuilder;

  public TestDataBuilder(SanctionedPersonDataBuilder sanctionedPersonDataBuilder) {
    this.sanctionedPersonDataBuilder = sanctionedPersonDataBuilder;
  }

  public TestDataBuilder addSanctionedPerson() {
    generatedPerson.setSanctionedPerson(sanctionedPersonDataBuilder.createSanctionedPerson());
    return this;
  }

  public GeneratedPerson build() {
    return generatedPerson;
  }
}