package com.example.sanctionedpeopleservice.common.error;

import com.example.sanctionedpeopleservice.common.error.model.ErrorStatus;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;

import java.util.List;

public record UseCaseError(String message, ErrorStatus status, List<ErrorDetail> errorDetails) {
}
