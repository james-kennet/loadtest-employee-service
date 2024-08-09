package com.employeeservice.databases.mysql.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
import com.employeeservice.databases.mysql.dao.impl.MySqlEmployeeDaoImpl;
import com.employeeservice.databases.mysql.dao.repository.MySqlEmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class EmployeeDaoImplTest {

  @Mock
  MySqlEmployeeRepository employeeRepository;

  @InjectMocks
  MySqlEmployeeDaoImpl employeeDao;

  private MySqlEmployee employee;

  @BeforeEach
  public void setUp() {
    employee = new MySqlEmployee();
    employee.setId(1L);
    employee.setFirstName("James Bond");
  }

  @Test
  void getEmployeeById() {
    when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

    Optional<MySqlEmployee> employeeOpt = employeeRepository.findById(anyLong());

    assertTrue(employeeOpt.isPresent());
    assertEquals(1L, employeeOpt.get().getId());
    assertEquals("James Bond", employeeOpt.get().getFirstName());
    verify(employeeRepository, times(1)).findById(anyLong());
  }

  @Test
  void getEmployeeById_employee_not_found() {
    when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

    Optional<MySqlEmployee> employeeOpt = employeeRepository.findById(anyLong());

    assertTrue(employeeOpt.isEmpty());
    verify(employeeRepository, times(1)).findById(anyLong());
  }

  @Test
  void getEmployeeByFirstName() {
    Pageable pageable = Pageable.unpaged();
    Page<MySqlEmployee> page = new PageImpl<>(List.of(employee));
    when(employeeRepository.findByFirstName(anyString(), eq(pageable))).thenReturn(page);

    Page<MySqlEmployee> result = employeeDao.getEmployeeByFirstName("James Bond", pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(employeeRepository, times(1)).findByFirstName(anyString(), eq(pageable));
  }
}
