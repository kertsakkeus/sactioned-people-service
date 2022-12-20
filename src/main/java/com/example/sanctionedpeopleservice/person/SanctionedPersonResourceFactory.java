package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonSearchResult;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonUpdateRequest;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SanctionedPersonResourceFactory {
  public SanctionedPersonResource createSanctionedPersonResource(SanctionedPersonSearchResult searchResult) {
    SanctionedPerson sanctionedPerson = searchResult.sanctionedPerson();

    return SanctionedPersonResource.builder()
        .id(sanctionedPerson.getId())
        .personName(sanctionedPerson.getPersonName())
        .build()
        .add(createSanctionedPersonLinks(sanctionedPerson.getPersonName()));
  }

  private List<Link> createSanctionedPersonLinks(String personName) {
    return List.of(
        linkTo(methodOn(SanctionedPersonController.class).updateSanctionedPerson(personName,
            SanctionedPersonUpdateRequest.builder().build()))
            .withRel("update-sanctioned-person"),
        linkTo(methodOn(SanctionedPersonController.class).deleteSanctionedPerson(personName))
            .withRel("delete-sanctioned-person")
    );
  }
}