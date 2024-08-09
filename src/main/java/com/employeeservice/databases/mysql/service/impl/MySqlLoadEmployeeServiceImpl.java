package com.employeeservice.databases.mysql.service.impl;

import com.employeeservice.constant.Constants;
import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import com.employeeservice.databases.mysql.dao.repository.MySqlEmployeeRepository;
import com.employeeservice.databases.mysql.service.MySqlLoadEmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MySqlLoadEmployeeServiceImpl implements MySqlLoadEmployeeService {

  private static final Logger log = LoggerFactory.getLogger(MySqlLoadEmployeeServiceImpl.class);

  @Autowired private MySqlEmployeeRepository employeeRepository;

  @PersistenceContext private EntityManager entityManager;

  @Transactional
  public void concurrentInsertEmployees() {
    log.info("Started - MySQL concurrentInsertEmployees execution.");

    Instant start = Instant.now();

    ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_COUNT);

    // Disabling constraints and indexes
    disableConstraintsAndIndexes();

    // Calculate number of records per thread
    int recordsPerThread = Constants.TOTAL_RECORDS / Constants.THREAD_COUNT;

    // Create and submit tasks
    IntStream.range(0, Constants.THREAD_COUNT)
        .forEach(
            threadIndex -> {
              executor.submit(
                  () -> {
                    List<MySqlEmployee> employees = new ArrayList<>(Constants.BATCH_SIZE);

                    for (int i = threadIndex * recordsPerThread;
                        i < (threadIndex + 1) * recordsPerThread;
                        i++) {
                      MySqlEmployee employee = new MySqlEmployee();
                      employee.setFirstName(Constants.firstNames[i % 10]);
                      employee.setLastName(Constants.lastNames[i % 10]);
                      employee.setAge(20 + ThreadLocalRandom.current().nextInt(21));
                      employee.setEmail(
                          Constants.firstNames[i % 10].toLowerCase()
                              + "."
                              + Constants.lastNames[i % 10].toLowerCase()
                              + (i + 1)
                              + "@example.com");
                      employee.setOccupation(Constants.occupations[i % 10]);
                      employee.setCreatedBy(Constants.CREATED_BY);
                      employee.setCreatedOn(new Date());

                      employees.add(employee);

                      if (employees.size() == Constants.BATCH_SIZE) {
                        employeeRepository.saveAll(employees);
                        employees.clear();
                      }
                    }

                    if (!employees.isEmpty()) {
                      employeeRepository.saveAll(employees);
                    }
                  });
            });

    executor.shutdown();
    while (!executor.isTerminated()) {
      // Wait for all threads to finish in order to document the speed.
    }

    // Enabling constraints and indexes
    enableConstraintsAndIndexes();

    long timeElapsed = Duration.between(start, Instant.now()).toMillis();
    log.info(
        "End - MySQL concurrentInsertEmployees timeElapsed in seconds: {}", timeElapsed / 1000);
  }

  private void disableConstraintsAndIndexes() {
    Query disableFK = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0");
    disableFK.executeUpdate();
    Query disableIndexes = entityManager.createNativeQuery("ALTER TABLE employees DISABLE KEYS");
    disableIndexes.executeUpdate();
  }

  private void enableConstraintsAndIndexes() {
    Query enableFK = entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1");
    enableFK.executeUpdate();
    Query enableIndexes = entityManager.createNativeQuery("ALTER TABLE employees ENABLE KEYS");
    enableIndexes.executeUpdate();
  }
}
