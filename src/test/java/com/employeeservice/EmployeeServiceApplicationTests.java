package com.employeeservice;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

class EmployeeServiceApplicationTests {

  @Test
  public void testMain() throws InterruptedException {
    try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
      EmployeeServiceApplication.main(new String[] {});
      mockedSpringApplication.verify(
          () -> SpringApplication.run(EmployeeServiceApplication.class, new String[] {}));
    }
  }
}
