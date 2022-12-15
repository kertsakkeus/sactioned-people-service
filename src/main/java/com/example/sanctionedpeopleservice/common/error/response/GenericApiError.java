package com.example.sanctionedpeopleservice.common.error.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenericApiError {
  private ApiError error;
}
