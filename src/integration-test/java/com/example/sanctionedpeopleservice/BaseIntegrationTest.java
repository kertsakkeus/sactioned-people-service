package com.example.sanctionedpeopleservice;

import io.restassured.http.Method;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(classes = SanctionedPeopleServiceApplication.class, webEnvironment = DEFINED_PORT)
@TestInstance(PER_CLASS)
@Sql(value = "classpath:truncate-table.sql", executionPhase = BEFORE_TEST_METHOD)
public abstract class BaseIntegrationTest {
  protected RequestSpecification spec;

  @Autowired
  protected TestDataBuilder testDataBuilder;

  @Value("${server.port}")
  protected int port;

  @PostConstruct
  public void postConstruct() {
    spec = given()
        .contentType(APPLICATION_JSON_VALUE)
        .port(port);
  }

  public ExtractableResponse<Response> getResponse(Method method, String path, int statusCode) {
    return given(spec)
        .request(method, path)
        .then()
        .assertThat()
        .statusCode(statusCode)
        .extract();
  }

  public <T> ExtractableResponse<Response> getResponseWithBody(Method method, String path,
                                                               int statusCode, T request) {
    return given(spec)
        .body(request)
        .request(method, path)
        .then()
        .assertThat()
        .statusCode(statusCode)
        .extract();
  }
}