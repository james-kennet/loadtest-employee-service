package com.employeeservice.databases.mysql.controller;

import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import com.employeeservice.databases.mysql.service.MySqlEmployeeService;
import com.employeeservice.databases.mysql.service.MySqlLoadEmployeeService;
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
@RequestMapping("/api/mysql")
public class MySqlEmployeeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MySqlEmployeeController.class);

  @Autowired private MySqlEmployeeService mySqlEmployeeService;

  @Autowired private MySqlLoadEmployeeService mySqlLoadEmployeeService;

  @GetMapping("/{id}")
  public ResponseEntity<MySqlEmployee> getEmployeeById(@PathVariable Long id) {
    MySqlEmployee employee = mySqlEmployeeService.getEmployeeById(id);
    return ResponseEntity.ok(employee);
  }

  @GetMapping("/name/{firstName}")
  public ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(
      @PathVariable String firstName,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "25") int size) {
    return mySqlEmployeeService.getEmployeeByFirstNamePageable(firstName, page, size);
  }

  @PostMapping
  public ResponseEntity<MySqlEmployee> saveEmployee(@RequestBody MySqlEmployee request) {
    MySqlEmployee employee = mySqlEmployeeService.save(request);
    return new ResponseEntity<>(employee, HttpStatus.CREATED);
  }

  @PostMapping("/concurrentInsertEmployees")
  public String concurrentInsertEmployees() {
    LOGGER.info("Concurrent Data Load started");
    mySqlLoadEmployeeService.concurrentInsertEmployees();
    return "Concurrent Data Load ended";
  }
}
