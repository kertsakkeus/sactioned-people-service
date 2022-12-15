package com.example.sanctionedpeopleservice.common.error.exception;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;

public class UseCaseErrorResponseException extends RuntimeException {
  private final transient UseCaseError useCaseError;

  public UseCaseErrorResponseException(UseCaseError useCaseError) {
    this.useCaseError = useCaseError;
  }

  public UseCaseError getResponseMessage() {
    return useCaseError;
  }
}
