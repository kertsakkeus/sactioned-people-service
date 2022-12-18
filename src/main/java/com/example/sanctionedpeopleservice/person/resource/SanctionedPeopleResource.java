package com.example.sanctionedpeopleservice.person.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SanctionedPeopleResource {
  private List<SanctionedPersonResource> sanctionedPeople;
}