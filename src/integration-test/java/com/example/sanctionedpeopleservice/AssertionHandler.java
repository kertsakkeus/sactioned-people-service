package com.example.sanctionedpeopleservice;

import com.example.sanctionedpeopleservice.common.error.response.ApiError;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_EXIST;
import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionHandler {
  public static void assertPersonNonExistenceError(Long personId, ApiError error) {
    assertEquals(HTTP_NOT_FOUND ,error.getStatus());
    assertEquals(PERSON_NOT_FOUND.name(), error.getErrorCode());
    assertEquals("Person does not exist", error.getMessage());
    assertEquals(1, error.getErrors().size());
    assertEquals("Person with id ".concat(personId.toString()).concat(" does not exist in sanctioned people list!"),
                 error.getErrors().get(0).getMessage());
  }

  public static void assertExistingPersonError(String personName, ApiError error) {
    assertEquals(HTTP_BAD_REQUEST, error.getStatus());
    assertEquals(PERSON_EXIST.name(), error.getErrorCode());
    assertEquals("Person exist", error.getMessage());
    assertEquals(1, error.getErrors().size());
    assertEquals("Person ".concat(personName).concat(" already exist in sanctioned people list!"),
                 error.getErrors().get(0).getMessage());
  }
}