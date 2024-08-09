package com.employeeservice.databases.mysql.dao;

import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MySqlEmployeeDao {

  Optional<MySqlEmployee> getEmployeeById(Long id);

  Page<MySqlEmployee> getEmployeeByFirstName(String firstName, Pageable pageable);

  MySqlEmployee save(MySqlEmployee employee);
}
