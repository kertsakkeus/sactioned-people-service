package com.example.sanctionedpeopleservice.person.validation.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonValidationResource {
  private String personName;
  private boolean sanctioned;
  private String description;
}