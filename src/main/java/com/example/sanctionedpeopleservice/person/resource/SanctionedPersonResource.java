package com.example.sanctionedpeopleservice.person.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SanctionedPersonResource extends RepresentationModel<SanctionedPersonResource> {
  private Long id;
  private String personName;
}