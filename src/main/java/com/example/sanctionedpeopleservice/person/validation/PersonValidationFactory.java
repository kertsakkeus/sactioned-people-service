package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.sanctionedpeopleservice.person.validation.model.ValidationMessage.NOT_SANCTIONED;
import static com.example.sanctionedpeopleservice.person.validation.model.ValidationMessage.SANCTIONED;

@Component
public class PersonValidationFactory {
  private static final float PERCENTAGE_OF_SUSPICIOUSNESS = 70f;

  public PersonValidationResource createValidationResource(List<PersonValidationResult> personValidationResults,
                                                           String providedPersonName) {

    for (PersonValidationResult person : personValidationResults) {
      if (person.percentage() >= PERCENTAGE_OF_SUSPICIOUSNESS) {
        return createValidationResource(providedPersonName, true,
                                        SANCTIONED.getDescription(person.percentage(), person.personName()));
      }
    }
    return createValidationResource(providedPersonName, false,
                                    NOT_SANCTIONED.getDescription(providedPersonName));
  }

  private PersonValidationResource createValidationResource(String providedPersonName,
                                                            boolean isSanctioned,
                                                            String description) {
    return PersonValidationResource.builder()
        .personName(providedPersonName)
        .isSanctioned(isSanctioned)
        .description(description)
        .build();
  }
}