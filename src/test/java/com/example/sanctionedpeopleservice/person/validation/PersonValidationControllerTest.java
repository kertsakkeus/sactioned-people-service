package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import com.example.sanctionedpeopleservice.person.validation.service.PersonValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonValidationControllerTest {
  private final PersonValidationService personValidationService = mock(PersonValidationService.class);
  private final PersonValidationFactory personValidationFactory = mock(PersonValidationFactory.class);
  private final PersonValidationController personValidationController = new PersonValidationController(personValidationService,
                                                                                                       personValidationFactory);

  @Test
  void validatePerson() {
    String personName = "personName";

    List<PersonValidationResult> personValidationResults = List.of(new PersonValidationResult(null, null));

    PersonValidationResource personValidationResource = PersonValidationResource.builder()
        .personName(personName)
        .isSanctioned(true)
        .description("description")
        .build();

    when(personValidationService.validatePerson(personName)).thenReturn(personValidationResults);
    when(personValidationFactory.createValidationResource(personValidationResults, personName)).thenReturn(personValidationResource);

    ResponseEntity<PersonValidationResource> responseEntity = personValidationController.validatePerson(personName);

    assertEquals(personName, requireNonNull(responseEntity.getBody()).getPersonName());
    assertEquals("description", requireNonNull(responseEntity.getBody()).getDescription());
    assertTrue(requireNonNull(responseEntity.getBody()).isSanctioned());
  }
}