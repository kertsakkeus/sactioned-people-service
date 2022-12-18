package com.example.sanctionedpeopleservice.person.validation;

import com.example.sanctionedpeopleservice.person.validation.model.ValidationMessage;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResource;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonValidationFactory {
  @Value("${percentage-of-suspiciousness}")
  private float percentageOfSuspiciousness;

  public PersonValidationResource createValidationResource(List<PersonValidationResult> personValidationResults,
                                                           String providedPersonName) {

    for (PersonValidationResult person : personValidationResults) {
      if (person.getPercentage() >= percentageOfSuspiciousness) {
        return createValidationResource(providedPersonName, true,
                                        ValidationMessage.SANCTIONED.getDescription(person.getPercentage(), person.getPersonName()));
      }
    }
    return createValidationResource(providedPersonName, false,
                                    ValidationMessage.NOT_SANCTIONED.getDescription(providedPersonName));
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