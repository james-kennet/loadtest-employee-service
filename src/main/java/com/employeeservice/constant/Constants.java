package com.employeeservice.constant;

public class Constants {

  public static final int TOTAL_RECORDS = 10_000_000;
  public static final int BATCH_SIZE = 2000;
  public static final int THREAD_COUNT = 10;

  public static final String[] firstNames = {
    "John", "Jane", "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry"
  };
  public static final String[] lastNames = {
    "Doe", "Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson"
  };

  public static String[] occupations = {
    "Software Engineer",
    "Data Scientist",
    "Product Manager",
    "UX Designer",
    "DevOps Engineer",
    "Business Analyst",
    "System Administrator",
    "HR Manager",
    "Marketing Specialist",
    "Test Engineer"
  };

  public static final String CREATED_BY = "EMPLOYEE_SERVICE_CREATE";
  public static final String UPDATED_BY = "EMPLOYEE_SERVICE_UPDATE";
}
