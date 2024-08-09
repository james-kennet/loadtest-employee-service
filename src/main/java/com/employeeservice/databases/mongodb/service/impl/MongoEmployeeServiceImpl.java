package com.employeeservice.databases.mongodb.service.impl;

import com.employeeservice.databases.mongodb.dao.MongoEmployeeDao;
import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import com.employeeservice.databases.mongodb.service.MongoEmployeeService;
import com.employeeservice.exception.EmployeeNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MongoEmployeeServiceImpl implements MongoEmployeeService {

  private static final Logger log = LoggerFactory.getLogger(MongoEmployeeServiceImpl.class);

  @Autowired private MongoEmployeeDao mongoEmployeeDao;

  public MongoEmployee getEmployeeById(String id) {
    Optional<MongoEmployee> empOpt = mongoEmployeeDao.getEmployeeById(id);
    if (empOpt.isEmpty()) {
      log.error("Mongodb Employee not found with id: {}", id);
      throw new EmployeeNotFoundException("Mongodb Employee not found with id: " + id);
    }
    return empOpt.get();
  }

  public ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(String firstName, int page, int size) {
    Page<MongoEmployee> employees = this.getEmployeeByFirstName(firstName, page, size);
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("totalItems", employees.getTotalElements());
    response.put("totalPages", employees.getTotalPages());
    response.put("currentPageTotal", employees.getNumberOfElements());
    response.put("currentPage", employees.getNumber());
    response.put("employees", employees.getContent());
    return ResponseEntity.ok(response);
  }

  public Page<MongoEmployee> getEmployeeByFirstName(String firstName, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<MongoEmployee> employees = mongoEmployeeDao.getEmployeeByFirstName(firstName, pageable);
    if (employees.isEmpty()) {
      throw new EmployeeNotFoundException("Mongodb Employees not found with firstName= " + firstName);
    }
    return employees;
  }

  public MongoEmployee save(MongoEmployee employee) {
    return mongoEmployeeDao.save(employee);
  }
}
