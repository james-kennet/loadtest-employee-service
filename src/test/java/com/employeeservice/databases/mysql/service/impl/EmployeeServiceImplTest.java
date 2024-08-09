package com.employeeservice.databases.mysql.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeeservice.constant.Constants;
import com.employeeservice.databases.mysql.service.impl.MySqlEmployeeServiceImpl;
import com.employeeservice.exception.EmployeeNotFoundException;
import com.employeeservice.databases.mysql.dao.MySqlEmployeeDao;
import com.employeeservice.databases.mysql.dao.entity.MySqlEmployee;
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
class EmployeeServiceImplTest {

  @Mock
  MySqlEmployeeDao employeeDao;

  @InjectMocks
  MySqlEmployeeServiceImpl employeeService;

  private MySqlEmployee employee;

  @BeforeEach
  public void setUp() {
    employee = new MySqlEmployee();
    employee.setId(1L);
    employee.setFirstName("James Bond");
  }

  @Test
  void getEmployeeById_success() {
    when(employeeDao.getEmployeeById(anyLong())).thenReturn(Optional.of(employee));

    Optional<MySqlEmployee> employeeOpt = employeeDao.getEmployeeById(anyLong());

    assertTrue(employeeOpt.isPresent());
    assertNotNull(employeeOpt.get());
    assertEquals("James Bond", employeeOpt.get().getFirstName());
    verify(employeeDao, times(1)).getEmployeeById(anyLong());
  }

  @Test
  void getEmployeeById_employee_not_found() {
    when(employeeDao.getEmployeeById(anyLong())).thenReturn(Optional.empty());

    Optional<MySqlEmployee> employeeOpt = employeeDao.getEmployeeById(anyLong());

    assertTrue(employeeOpt.isEmpty());
    verify(employeeDao, times(1)).getEmployeeById(anyLong());
  }

  @Test
  void getEmployeeById_employee_not_found_throw_exception() {
    when(employeeDao.getEmployeeById(anyLong())).thenThrow(EmployeeNotFoundException.class);

    assertThrows(
        EmployeeNotFoundException.class,
        () -> {
          employeeDao.getEmployeeById(anyLong());
        });
  }

  @Test
  void getEmployeeByFirstName() {
    Pageable pageable = Pageable.unpaged();
    Page<MySqlEmployee> page = new PageImpl<>(List.of(employee));
    when(employeeDao.getEmployeeByFirstName(anyString(), eq(pageable))).thenReturn(page);

    Page<MySqlEmployee> result = employeeDao.getEmployeeByFirstName("James Bond", pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(employeeDao, times(1)).getEmployeeByFirstName(anyString(), eq(pageable));
  }

  @Test
  void getEmployeeByFirstName_employee_not_found() {
    Pageable pageable = Pageable.unpaged();
    when(employeeDao.getEmployeeByFirstName(anyString(), eq(pageable))).thenReturn(Page.empty());

    Page<MySqlEmployee> result = employeeDao.getEmployeeByFirstName("James Bond", pageable);

    assertNotNull(result);
    assertEquals(0, result.getTotalElements());
    verify(employeeDao, times(1)).getEmployeeByFirstName(anyString(), eq(pageable));
  }

  @Test
  void getEmployeeByFirstName_employee_not_found_throw_exception() {
    Pageable pageable = Pageable.unpaged();
    when(employeeDao.getEmployeeByFirstName(anyString(), eq(pageable)))
        .thenThrow(EmployeeNotFoundException.class);

    assertThrows(
        EmployeeNotFoundException.class,
        () -> {
          employeeDao.getEmployeeByFirstName("James Bond", pageable);
        });
  }

  @Test
  void save() {
    employee.setCreatedBy(Constants.CREATED_BY);
    when(employeeDao.save(any())).thenReturn(employee);

    MySqlEmployee employee = employeeDao.save(new MySqlEmployee());
    assertNotNull(employee);
    assertEquals(1, employee.getId());
  }
}
