package com.example.sanctionedpeopleservice.common;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.exception.UseCaseErrorResponseException;
import com.example.sanctionedpeopleservice.common.error.model.ErrorStatus;
import com.example.sanctionedpeopleservice.common.error.response.ApiError;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.common.error.response.GenericApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class GlobalControllerExceptionHandler {
  public static final String UNEXPECTED_EXCEPTION = "Unexpected exception";

  @ExceptionHandler
  protected ResponseEntity<Object> handleUnexpectedException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(new GenericApiError(ApiError.builder()
            .timestamp(now())
            .errorCode(INTERNAL_SERVER_ERROR.name())
            .message(UNEXPECTED_EXCEPTION)
            .status(INTERNAL_SERVER_ERROR.value())
            .errors(List.of(ErrorDetail.builder()
                .message(UNEXPECTED_EXCEPTION)
                .build()))
            .build()));
  }

  @ExceptionHandler(value = {UseCaseErrorResponseException.class})
  protected ResponseEntity<Object> handle(UseCaseErrorResponseException ex) {
    UseCaseError useCaseError = ex.getResponseMessage();
    HttpStatus httpStatus = resolveHttpStatus(useCaseError.getStatus());

    return ResponseEntity.status(httpStatus)
        .body(new GenericApiError(ApiError.builder()
            .timestamp(now())
            .errorCode(useCaseError.getStatus().name())
            .message(useCaseError.getMessage())
            .status(httpStatus.value())
            .errors(useCaseError.getErrorDetails())
            .build()));
  }

  private HttpStatus resolveHttpStatus(ErrorStatus errorStatus) {
    switch (errorStatus) {
      case SANCTIONED_PERSON_NOT_FOUND:
        return NOT_FOUND;
    }
    return INTERNAL_SERVER_ERROR;
  }
}
