package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.BaseIntegrationTest;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonRequest;
import com.example.sanctionedpeopleservice.resource.GeneratedPerson;
import org.junit.jupiter.api.Test;

import static com.example.sanctionedpeopleservice.AssertionHandler.assertExistingPersonError;
import static com.example.sanctionedpeopleservice.model.Path.SANCTIONED_PEOPLE;
import static io.restassured.http.Method.POST;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class AddSanctionedPersonUseCaseIntegrationTest extends BaseIntegrationTest {
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

    assertExistingPersonError(generatedPerson.getSanctionedPerson().getPersonName(), error);
  }
}