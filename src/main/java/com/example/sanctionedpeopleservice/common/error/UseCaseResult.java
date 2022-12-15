package com.example.sanctionedpeopleservice.common.error;

import lombok.Getter;

import java.util.Optional;

@Getter
public class UseCaseResult<T> {
  private final T result;
  private final UseCaseError error;

  public UseCaseResult(T result) {
    this.result = result;
    this.error = null;
  }

  public UseCaseResult(UseCaseError error) {
    this.error = error;
    this.result = null;
  }

  public Optional<UseCaseError> getError() {
    return Optional.ofNullable(error);
  }
}
