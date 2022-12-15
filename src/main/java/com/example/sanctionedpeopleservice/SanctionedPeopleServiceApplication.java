package com.example.sanctionedpeopleservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
    title = "Sanctioned People API",
    version = "1.0",
    description = "Service that compares a given name against a list of sanctioned people to detect suspicious transfers."
))
@SpringBootApplication
public class SanctionedPeopleServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(SanctionedPeopleServiceApplication.class, args);
  }
}
