package com.employeeservice.databases.mongodb.dao.repository;

import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEmployeeRepository extends MongoRepository<MongoEmployee, String> {

  @Query("SELECT e FROM MongoEmployee e WHERE upper(e.firstName) = upper(?1)")
  Page<MongoEmployee> findByFirstName(String firstName, final Pageable pageable);
}
