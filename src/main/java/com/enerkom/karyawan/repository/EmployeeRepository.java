package com.enerkom.karyawan.repository;

import com.enerkom.karyawan.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee WHERE position = :position", nativeQuery = true)
    List<Employee> findByPosition(@Param("position") String position);
}
