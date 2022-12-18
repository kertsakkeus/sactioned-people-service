package com.example.sanctionedpeopleservice.person.validation.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonValidationResource {
  private String personName;
  private boolean isSanctioned;
  private String description;
}