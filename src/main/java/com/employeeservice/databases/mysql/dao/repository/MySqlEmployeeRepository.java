package com.employeeservice.databases.mysql.dao.repository;

import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlEmployeeRepository extends JpaRepository<MySqlEmployee, Long> {

  @Query("SELECT e FROM MySqlEmployee e WHERE upper(e.firstName) = upper(?1)")
  Page<MySqlEmployee> findByFirstName(String firstName, final Pageable pageable);
}
