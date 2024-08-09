package com.employeeservice.databases.mongodb.dao;

import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MongoEmployeeDao {

  Optional<MongoEmployee> getEmployeeById(String id);

  Page<MongoEmployee> getEmployeeByFirstName(String firstName, Pageable pageable);

  MongoEmployee save(MongoEmployee employee);
}
