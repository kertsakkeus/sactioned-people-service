package com.example.sanctionedpeopleservice.model;

public enum Path {
  SANCTIONED_PERSON("/sanctions/%s"),
  SANCTIONED_PEOPLE("/sanctions"),
  PERSON_VALIDATION("/sanctions/validation/%s");

  private final String path;

  Path(String path) {
    this.path = path;
  }

  public String getPath(Object... args) {
    return String.format(path, args);
  }
}