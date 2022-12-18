package com.example.sanctionedpeopleservice.person.validation.resource;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PersonValidationResult {
  private String personName;
  private Float percentage;
}