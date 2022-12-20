package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonRequest;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import static com.example.sanctionedpeopleservice.AssertionHandler.assertExistingPersonError;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PEOPLE;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PERSON;
import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.POST;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddSanctionedPersonUseCaseIntegrationTest extends BaseIntegrationTest {
  @Test
  void addSanctionedPerson() {
    String expectedPersonName = "TEST PERSON";

    SanctionedPersonRequest sanctionedPersonRequest = SanctionedPersonRequest.builder()
        .personName(expectedPersonName)
        .build();

    String location = getResponseWithBody(POST, SANCTIONED_PEOPLE.getPath(), HTTP_CREATED, sanctionedPersonRequest)
        .header("Location");

    Long personId = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));

    SanctionedPersonResource sanctionedPersonResource = getResponse(GET, SANCTIONED_PERSON
        .getPath(personId), HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", SanctionedPersonResource.class);

    assertEquals(expectedPersonName, sanctionedPersonResource.getPersonName());
  }

  @Test
  void addSanctionedPerson_AlreadyExists() {
    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    SanctionedPersonRequest sanctionedPersonRequest = SanctionedPersonRequest.builder()
        .personName(generatedPerson.getSanctionedPerson().getPersonName())
        .build();

    ApiError error = getResponseWithBody(POST, SANCTIONED_PEOPLE.getPath(),
                                         HTTP_BAD_REQUEST, sanctionedPersonRequest)
        .body()
        .jsonPath()
        .getObject("", GenericApiError.class).getError();

    assertExistingPersonError(generatedPerson.getSanctionedPerson().getId(), error);
  }
}