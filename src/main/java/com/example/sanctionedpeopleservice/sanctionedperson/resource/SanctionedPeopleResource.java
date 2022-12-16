package com.example.sanctionedpeopleservice.sanctionedperson.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SanctionedPeopleResource {
  private List<SanctionedPersonResource> sanctionedPeople;
}