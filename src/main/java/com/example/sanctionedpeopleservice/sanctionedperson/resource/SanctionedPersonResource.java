package com.example.sanctionedpeopleservice.sanctionedperson.resource;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class SanctionedPersonResource extends RepresentationModel<SanctionedPersonResource> {
  private Long id;
  private String personName;
}