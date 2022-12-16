package com.example.sanctionedpeopleservice.sanctionedperson.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SanctionedPersonRequest {
  private String personName;
}
