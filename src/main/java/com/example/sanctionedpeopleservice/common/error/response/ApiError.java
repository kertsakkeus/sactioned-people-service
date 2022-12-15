package com.example.sanctionedpeopleservice.common.error.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private LocalDateTime timestamp;
  private int status;
  private String errorCode;
  private String message;
  private List<ErrorDetail> errors;
}
