package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPeopleResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.sanctionedpeopleservice.AssertionHandler.assertPersonNonExistenceError;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PEOPLE;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PERSON;
import static io.restassured.http.Method.GET;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindSanctionedPersonUseCaseIntegrationTest extends BaseIntegrationTest {
  @Test
  void getSanctionedPerson() {
    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    SanctionedPersonResource sanctionedPersonResource = getResponse(GET, SANCTIONED_PERSON
        .getPath(generatedPerson.getSanctionedPerson().getPersonName()), HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", SanctionedPersonResource.class);

    assertSanctionedPerson(generatedPerson, sanctionedPersonResource);
  }

  @Test
  void getSanctionedPeople() {
    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    List<SanctionedPersonResource> sanctionedPersonResources = getResponse(GET, SANCTIONED_PEOPLE.getPath(), HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", SanctionedPeopleResource.class).getSanctionedPeople();

    assertEquals(1, sanctionedPersonResources.size());
    assertSanctionedPerson(generatedPerson, sanctionedPersonResources.get(0));
  }

  @Test
  void getSanctionedPerson_NotFound() {
    String personName = "personName";

    ApiError error = getResponse(GET, SANCTIONED_PERSON.getPath(personName), HTTP_NOT_FOUND)
        .body()
        .jsonPath()
        .getObject("", GenericApiError.class).getError();

    assertPersonNonExistenceError(personName, error);
  }

  private void assertSanctionedPerson(GeneratedPerson generatedPerson, SanctionedPersonResource sanctionedPersonResource) {
    assertEquals(generatedPerson.getSanctionedPerson().getId(), sanctionedPersonResource.getId());
    assertEquals(generatedPerson.getSanctionedPerson().getPersonName(), sanctionedPersonResource.getPersonName());
    assertTrue(sanctionedPersonResource.getLinks().hasSize(2));
  }
}