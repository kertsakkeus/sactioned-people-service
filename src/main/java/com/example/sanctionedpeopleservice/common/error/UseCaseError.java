package com.example.sanctionedpeopleservice.common.error;

import com.example.sanctionedpeopleservice.common.error.model.ErrorStatus;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class UseCaseError {
  private final String message;
  private final ErrorStatus status;
  private final List<ErrorDetail> errorDetails;

  public UseCaseError(String message, ErrorStatus status, List<ErrorDetail> errorDetails) {
    this.message = message;
    this.status = status;
    this.errorDetails = errorDetails;
  }
}
