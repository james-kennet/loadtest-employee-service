package com.employeeservice.databases.mysql.service.impl;

import com.employeeservice.constant.Constants;
import com.employeeservice.databases.mysql.dao.MySqlEmployeeDao;
import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import com.employeeservice.databases.mysql.service.MySqlEmployeeService;
import com.employeeservice.exception.EmployeeNotFoundException;
import java.util.Date;
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
public class MySqlEmployeeServiceImpl implements MySqlEmployeeService {

  private static final Logger log = LoggerFactory.getLogger(MySqlEmployeeServiceImpl.class);

  @Autowired private MySqlEmployeeDao mySqlEmployeeDao;

  public MySqlEmployee getEmployeeById(Long id) {
    Optional<MySqlEmployee> empOpt = mySqlEmployeeDao.getEmployeeById(id);
    if (empOpt.isEmpty()) {
      log.error("Employee not found with id: {}", id);
      throw new EmployeeNotFoundException("Employee not found with id: " + id);
    }
    return empOpt.get();
  }

  public ResponseEntity<Map<String, Object>> getEmployeeByFirstNamePageable(String firstName, int page, int size) {
    Page<MySqlEmployee> employees = this.getEmployeeByFirstName(firstName, page, size);
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("totalItems", employees.getTotalElements());
    response.put("totalPages", employees.getTotalPages());
    response.put("currentPageTotal", employees.getNumberOfElements());
    response.put("currentPage", employees.getNumber());
    response.put("employees", employees.getContent());
    return ResponseEntity.ok(response);
  }

  public Page<MySqlEmployee> getEmployeeByFirstName(String firstName, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<MySqlEmployee> employees = mySqlEmployeeDao.getEmployeeByFirstName(firstName, pageable);
    if (employees.isEmpty()) {
      throw new EmployeeNotFoundException("Employee not found with firstName= " + firstName);
    }
    return employees;
  }

  public MySqlEmployee save(MySqlEmployee employee) {
    employee.setCreatedBy(Constants.CREATED_BY);
    employee.setCreatedOn(new Date());
    return mySqlEmployeeDao.save(employee);
  }
}
