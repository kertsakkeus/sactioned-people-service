package com.example.sanctionedpeopleservice.person.validation.model;

public enum ValidationMessage {
  SANCTIONED("There is a %s%% probability that it is %s!"),
  NOT_SANCTIONED("%s is not a sanctioned person!");

  private final String description;

  ValidationMessage(String description) {
    this.description = description;
  }

  public String getDescription(Object... args) {
    return String.format(description, args);
  }
}