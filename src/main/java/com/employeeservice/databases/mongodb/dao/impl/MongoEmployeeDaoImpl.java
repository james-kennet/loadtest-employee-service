package com.employeeservice.databases.mongodb.dao.impl;

import com.employeeservice.databases.mongodb.dao.MongoEmployeeDao;
import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import com.employeeservice.databases.mongodb.dao.repository.MongoEmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MongoEmployeeDaoImpl implements MongoEmployeeDao {

  private static final Logger log = LoggerFactory.getLogger(MongoEmployeeDaoImpl.class);

  @Autowired private MongoEmployeeRepository mongoEmployeeRepository;

  public Optional<MongoEmployee> getEmployeeById(String id) {
    return mongoEmployeeRepository.findById(id);
  }

  public Page<MongoEmployee> getEmployeeByFirstName(String firstName, Pageable pageable) {
    return mongoEmployeeRepository.findByFirstName(firstName, pageable);
  }

  public MongoEmployee save(MongoEmployee employee) {
    return mongoEmployeeRepository.save(employee);
  }
}
