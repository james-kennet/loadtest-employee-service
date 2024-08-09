package com.employeeservice.databases.mongodb.controller;

import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import com.employeeservice.databases.mongodb.service.MongoEmployeeService;
import com.employeeservice.databases.mongodb.service.MongoLoadEmployeeService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mongodb")
public class MongoEmployeeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoEmployeeController.class);

  @Autowired private MongoEmployeeService mongoEmployeeService;

  @Autowired private MongoLoadEmployeeService mongoLoadEmployeeService;

  @GetMapping("/{id}")
  public ResponseEntity<MongoEmployee> getEmployeeById(@PathVariable String id) {
    return ResponseEntity.ok(mongoEmployeeService.getEmployeeById(id));
  }

  @GetMapping("/name/{firstName}")
  public ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(
      @PathVariable String firstName,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "25") int size) {
    return mongoEmployeeService.getEmployeeByFirstNamePageable(firstName, page, size);
  }

  @PostMapping
  public ResponseEntity<MongoEmployee> saveEmployee(@RequestBody MongoEmployee request) {
    MongoEmployee employee = mongoEmployeeService.save(request);
    return new ResponseEntity<>(employee, HttpStatus.CREATED);
  }

  @PostMapping("/concurrentInsertEmployees")
  public String concurrentInsertEmployees() {
    LOGGER.info("Concurrent Mongodb Data Load started");
    mongoLoadEmployeeService.concurrentInsertEmployees();
    return "Concurrent Mongodb Data Load ended";
  }
}
