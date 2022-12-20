package com.example.sanctionedpeopleservice.person.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SanctionedPeopleResource {
  private List<SanctionedPersonResource> sanctionedPeople;
}