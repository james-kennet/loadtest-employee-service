package com.employeeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

  private int statusCode;
  private String error;
  private String message;
}
