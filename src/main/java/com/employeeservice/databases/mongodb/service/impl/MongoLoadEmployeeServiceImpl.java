package com.employeeservice.databases.mongodb.service.impl;

import com.employeeservice.constant.Constants;
import com.employeeservice.databases.mongodb.dao.document.MongoEmployee;
import com.employeeservice.databases.mongodb.service.MongoLoadEmployeeService;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MongoLoadEmployeeServiceImpl implements MongoLoadEmployeeService {

  private static final Logger log = LoggerFactory.getLogger(MongoLoadEmployeeService.class);

  @Autowired private MongoRepository mongoRepository;

  @Transactional
  public void concurrentInsertEmployees() {
    log.info("Started - Mongodb concurrentInsertEmployees execution.");

    Instant start = Instant.now();

    ExecutorService executor = Executors.newFixedThreadPool(Constants.THREAD_COUNT);

    // Calculate number of records per thread
    int recordsPerThread = Constants.TOTAL_RECORDS / Constants.THREAD_COUNT;

    // Create and submit tasks
    IntStream.range(0, Constants.THREAD_COUNT)
        .forEach(
            threadIndex -> {
              executor.submit(
                  () -> {
                    List<MongoEmployee> employees = new ArrayList<>(Constants.BATCH_SIZE);
                    for (int i = threadIndex * recordsPerThread;
                        i < (threadIndex + 1) * recordsPerThread;
                        i++) {
                      MongoEmployee employee = new MongoEmployee();
                      employee.setId(String.valueOf(i + 1));
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

                      employees.add(employee);

                      if (employees.size() == Constants.BATCH_SIZE) {
                        mongoRepository.saveAll(employees);
                        employees.clear();
                      }
                    }

                    if (!employees.isEmpty()) {
                      mongoRepository.saveAll(employees);
                    }
                  });
            });

    executor.shutdown();
    while (!executor.isTerminated()) {
      // Wait for all threads to finish in order to document the speed.
    }
    long timeElapsed = Duration.between(start, Instant.now()).toMillis();
    log.info(
        "End - Mongodb concurrentInsertEmployees timeElapsed in seconds: {}", timeElapsed / 1000);
  }
}
