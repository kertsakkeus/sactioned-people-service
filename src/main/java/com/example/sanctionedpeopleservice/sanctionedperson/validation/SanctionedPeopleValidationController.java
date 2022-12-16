package com.example.sanctionedpeopleservice.sanctionedperson.validation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class SanctionedPeopleValidationController {

  @Operation(summary = "Validate sanctioned person name")
  @GetMapping(value = "/sanctions/validation/{personName}")
  public ResponseEntity<Object> validatePerson(@PathVariable String personName) {
    return ResponseEntity.ok(personName);
  }
}