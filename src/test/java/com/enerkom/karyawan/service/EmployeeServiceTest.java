package com.enerkom.karyawan.service;

import com.enerkom.karyawan.entity.Employee;
import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmployee() {
        var emp = new Employee();
        emp.setId(1L);
        emp.setUsers(new Users());

        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(emp);

        Employee empService = employeeService.saveEmployee(new Users());
        assertNotNull(empService);
        assertNotNull(empService.getUsers());
        assertNotNull(empService.getCreatedAt());
        assertNotNull(empService.getRegistrationNumber());
        System.out.println(empService.getCreatedAt());
        System.out.println(empService.getRegistrationNumber());
        Mockito.verify(employeeRepository).save(any(Employee.class));
    }
}