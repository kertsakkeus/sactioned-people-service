package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.person.validation.model.ValidationMessage;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonValidationFactoryTest {
  private final PersonValidationFactory personValidationFactory = new PersonValidationFactory();

  private final String personName = "personName";

  @Test
  void createValidationResource_Sanctioned() {
    float percentage = 72.22f;
    List<PersonValidationResult> personValidationResults = List.of(new PersonValidationResult(personName, percentage));

    PersonValidationResource personValidationResource = personValidationFactory.createValidationResource(personValidationResults,
                                                                                                         personName);

    assertEquals(personName, personValidationResource.getPersonName());
    assertTrue(personValidationResource.isSanctioned());
    assertEquals(ValidationMessage.SANCTIONED.getDescription(percentage, personName), personValidationResource.getDescription());
  }

  @Test
  void createValidationResource_NotSanctioned() {
    List<PersonValidationResult> personValidationResults = List.of(new PersonValidationResult(personName, 50f));

    PersonValidationResource personValidationResource = personValidationFactory.createValidationResource(personValidationResults,
                                                                                                         personName);

    assertEquals(personName, personValidationResource.getPersonName());
    assertFalse(personValidationResource.isSanctioned());
    assertEquals(ValidationMessage.NOT_SANCTIONED.getDescription(personName), personValidationResource.getDescription());
  }
}