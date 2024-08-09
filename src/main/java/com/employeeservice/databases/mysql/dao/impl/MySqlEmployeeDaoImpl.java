package com.employeeservice.databases.mysql.dao.impl;

import com.employeeservice.databases.mysql.dao.MySqlEmployeeDao;
import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import com.employeeservice.databases.mysql.dao.repository.MySqlEmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlEmployeeDaoImpl implements MySqlEmployeeDao {

  private static final Logger log = LoggerFactory.getLogger(MySqlEmployeeDaoImpl.class);

  @Autowired private MySqlEmployeeRepository employeeRepository;

  public Optional<MySqlEmployee> getEmployeeById(Long id) {
    return employeeRepository.findById(id);
  }

  public Page<MySqlEmployee> getEmployeeByFirstName(String firstName, Pageable pageable) {
    return employeeRepository.findByFirstName(firstName, pageable);
  }

  public MySqlEmployee save(MySqlEmployee employee) {
    log.info("employee before save {}", employee.toString());
    employee = employeeRepository.save(employee);
    log.info("employee after save {}", employee.toString());
    return employee;
  }
}
