package com.enerkom.karyawan.service;

import com.enerkom.karyawan.entity.Employee;
import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Users users){
        var employee = new Employee();
        employee.setUsers(users);

        return this.employeeRepository.save(employee);
    }
}
