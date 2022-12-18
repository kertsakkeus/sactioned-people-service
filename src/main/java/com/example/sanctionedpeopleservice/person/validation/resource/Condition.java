package com.example.sanctionedpeopleservice.person.validation.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Condition {
  private final Character character;
  private final Long sum;
}