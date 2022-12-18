package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.person.validation.service.PersonValidationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class PersonValidationController {
  private final PersonValidationService personValidationService;
  private final PersonValidationFactory personValidationFactory;

  public PersonValidationController(PersonValidationService personValidationService,
                                    PersonValidationFactory personValidationFactory) {
    this.personValidationService = personValidationService;
    this.personValidationFactory = personValidationFactory;
  }

  @Operation(summary = "Validate person name against sanctioned people list")
  @GetMapping(value = "/sanctions/validation/{personName}")
  public ResponseEntity<PersonValidationResource> validatePerson(@PathVariable String personName) {
    return ResponseEntity.ok(personValidationFactory.createValidationResource(
        personValidationService.validatePerson(personName), personName));
  }
}