package com.enerkom.karyawan.repository;

import com.enerkom.karyawan.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {


    @Query(value = "SELECT * FROM roles where role_name = :role_name", nativeQuery = true)
    List<Roles> findRolesByERole(@Param("role_name") String roleName);

    @Query(value = "SELECT * FROM roles", nativeQuery = true)
    List<Roles> findRoleByRoleName();
}
