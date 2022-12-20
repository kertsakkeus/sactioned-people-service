package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonUpdateRequest;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import static com.example.sanctionedpeopleservice.AssertionHandler.assertPersonNonExistenceError;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PERSON;
import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.PUT;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateSanctionedPersonUseCaseIntegrationTest extends BaseIntegrationTest {
  @Test
  void updateSanctionedPerson() {
    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName("UPDATED PERSON")
        .build();

    getResponseWithBody(PUT, SANCTIONED_PERSON.getPath(generatedPerson.getSanctionedPerson().getId()),
                        HTTP_NO_CONTENT, updateRequest);

    SanctionedPersonResource sanctionedPersonResource = getResponse(GET, SANCTIONED_PERSON
        .getPath(generatedPerson.getSanctionedPerson().getId()), HTTP_OK)
        .body()
        .jsonPath()
        .getObject("", SanctionedPersonResource.class);

    assertEquals(generatedPerson.getSanctionedPerson().getId(), sanctionedPersonResource.getId());
    assertEquals(updateRequest.getNewPersonName(), sanctionedPersonResource.getPersonName());
  }

  @Test
  void updateSanctionedPerson_NotFound() {
    Long personId = 1L;

    ApiError error = getResponseWithBody(PUT, SANCTIONED_PERSON.getPath(personId),
                                         HTTP_NOT_FOUND, new SanctionedPersonUpdateRequest())
        .body()
        .jsonPath()
        .getObject("", GenericApiError.class).getError();

    assertPersonNonExistenceError(personId, error);
  }
}