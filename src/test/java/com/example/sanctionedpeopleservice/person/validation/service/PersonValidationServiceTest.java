package com.example.sanctionedpeopleservice.person.validation.service;

import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonValidationServiceTest {
  private final SanctionedPersonRepository sanctionedPersonRepository = mock(SanctionedPersonRepository.class);
  private final PersonValidationService personValidationService = new PersonValidationService(sanctionedPersonRepository);

  @Test
  void validatePerson() {
    String providedPersonName = "persoNam";
    String personName = "personName";
    String personName2 = "sanctioned";

    when(sanctionedPersonRepository.findAll()).thenReturn(List.of(createSanctionedPerson(1L, personName),
                                                                  createSanctionedPerson(2L, personName2)));

    List<PersonValidationResult> personValidationResults = personValidationService.validatePerson(providedPersonName);

    assertEquals(personName, personValidationResults.get(0).personName());
    assertEquals(75.0f, personValidationResults.get(0).percentage());
    assertEquals(personName2, personValidationResults.get(1).personName());
    assertEquals(44.444447f, personValidationResults.get(1).percentage());
  }

  private SanctionedPerson createSanctionedPerson(Long id, String personName) {
    return SanctionedPerson.builder()
        .id(id)
        .personName(personName)
        .build();
  }
}