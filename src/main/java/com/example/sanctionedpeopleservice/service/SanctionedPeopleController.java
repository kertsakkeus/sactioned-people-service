package com.example.sanctionedpeopleservice.service;

import com.example.sanctionedpeopleservice.service.resource.SanctionedPersonRequest;
import com.example.sanctionedpeopleservice.service.usecase.AddSanctionedPeopleUseCase;
import com.example.sanctionedpeopleservice.service.usecase.DeleteSanctionedPeopleUseCase;
import com.example.sanctionedpeopleservice.service.usecase.UpdateSanctionedPeopleUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class SanctionedPeopleController {
  private final AddSanctionedPeopleUseCase addSanctionedPeopleUseCase;
  private final UpdateSanctionedPeopleUseCase updateSanctionedPeopleUseCase;
  private final DeleteSanctionedPeopleUseCase deleteSanctionedPeopleUseCase;

  public SanctionedPeopleController(AddSanctionedPeopleUseCase addSanctionedPeopleUseCase,
                                    UpdateSanctionedPeopleUseCase updateSanctionedPeopleUseCase,
                                    DeleteSanctionedPeopleUseCase deleteSanctionedPeopleUseCase) {
    this.addSanctionedPeopleUseCase = addSanctionedPeopleUseCase;
    this.updateSanctionedPeopleUseCase = updateSanctionedPeopleUseCase;
    this.deleteSanctionedPeopleUseCase = deleteSanctionedPeopleUseCase;
  }

  @GetMapping(value = "/sanctions/{personName}")
  ResponseEntity<Object> getName(@PathVariable String personName) {
    return ResponseEntity.ok(personName);
  }

  @PostMapping(value = "/sanctions/{personName}")
  ResponseEntity<Object> addSanctionedPerson(@RequestBody SanctionedPersonRequest request) {
    return ResponseEntity.ok(request);
  }

  @PutMapping(value = "/sanctions")
  ResponseEntity<Object> updateSanctionedPerson(@PathVariable String personName) {
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/sanctions/{personName}")
  ResponseEntity<Object> removeSanctionedPerson(@PathVariable String personName) {
    return ResponseEntity.noContent().build();
  }
}
