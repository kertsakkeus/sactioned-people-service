package com.example.sanctionedpeopleservice.person.validation.resource;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SanctionedPersonCondition {
  private final String personName;
  private final List<Condition> conditions;
}