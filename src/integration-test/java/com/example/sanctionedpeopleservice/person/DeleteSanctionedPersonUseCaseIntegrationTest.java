package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import static com.example.sanctionedpeopleservice.AssertionHandler.assertPersonNonExistenceError;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PERSON;
import static io.restassured.http.Method.DELETE;
import static io.restassured.http.Method.GET;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

public class DeleteSanctionedPersonUseCaseIntegrationTest extends BaseIntegrationTest {
  @Test
  void deleteSanctionedPerson() {
    GeneratedPerson generatedPerson = testDataBuilder
        .addSanctionedPerson()
        .build();

    getResponse(DELETE, SANCTIONED_PERSON
        .getPath(generatedPerson.getSanctionedPerson().getId()), HTTP_NO_CONTENT);

    ApiError apiError = getResponse(GET, SANCTIONED_PERSON
        .getPath(generatedPerson.getSanctionedPerson().getId()), HTTP_NOT_FOUND)
        .body()
        .jsonPath()
        .getObject("", GenericApiError.class).getError();

    assertPersonNonExistenceError(generatedPerson.getSanctionedPerson().getId(), apiError);
  }
}