package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonSearchResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SanctionedPersonResourceFactoryTest {
  private final SanctionedPersonResourceFactory sanctionedPersonResourceFactory = new SanctionedPersonResourceFactory();

  private final String personName = "personName";

  @Test
  void createSanctionedPersonResource() {
    SanctionedPersonSearchResult searchResult = new SanctionedPersonSearchResult(createSanctionedPerson());

    SanctionedPersonResource resource = sanctionedPersonResourceFactory.createSanctionedPersonResource(searchResult);

    assertEquals(1L, resource.getId());
    assertEquals(personName, resource.getPersonName());
    assertTrue(resource.getLinks().hasSize(2));
  }

  private SanctionedPerson createSanctionedPerson() {
    return SanctionedPerson.builder()
        .id(1L)
        .personName(personName)
        .build();
  }
}