package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import static com.example.sanctionedpeopleservice.model.Path.PERSON_VALIDATION;
import static io.restassured.http.Method.GET;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonValidationServiceIntegrationTest extends BaseIntegrationTest {
  @Test
  void validatePerson_GreaterThanSeventyPercent() {
    String providedPersonName = "TET PERSN";

    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    PersonValidationResource personValidationResource = getResponse(GET, PERSON_VALIDATION.getPath(providedPersonName),
                                                                    HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", PersonValidationResource.class);

    assertEquals(providedPersonName, personValidationResource.getPersonName());
    assertTrue(personValidationResource.isSanctioned());
    assertEquals("There is a 71.42857% probability that it is "
                     .concat(generatedPerson.getSanctionedPerson().getPersonName()).concat("!"),
                 personValidationResource.getDescription());
  }

  @Test
  void validatePerson_LessThanSeventyPercent() {
    String providedPersonName = "TEST PERSON";

    PersonValidationResource personValidationResource = getResponse(GET, PERSON_VALIDATION.getPath(providedPersonName),
                                                                    HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", PersonValidationResource.class);

    assertEquals(providedPersonName, personValidationResource.getPersonName());
    assertFalse(personValidationResource.isSanctioned());
    assertEquals(providedPersonName.concat(" is not a sanctioned person!"), personValidationResource.getDescription());
  }
}