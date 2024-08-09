package com.employeeservice.databases.mongodb.service;

import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MongoEmployeeService {

  MongoEmployee getEmployeeById(String id);

  ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(String firstName, int page, int size);

  Page<MongoEmployee> getEmployeeByFirstName(String firstName, int page, int size);

  MongoEmployee save(MongoEmployee employee);
}
