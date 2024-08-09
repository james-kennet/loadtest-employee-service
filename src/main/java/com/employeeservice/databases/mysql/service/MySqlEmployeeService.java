package com.employeeservice.databases.mysql.service;

import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MySqlEmployeeService {

  MySqlEmployee getEmployeeById(Long id);

  ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(String firstName, int page, int size);

  Page<MySqlEmployee> getEmployeeByFirstName(String firstName, int page, int size);

  MySqlEmployee save(MySqlEmployee employee);
}
