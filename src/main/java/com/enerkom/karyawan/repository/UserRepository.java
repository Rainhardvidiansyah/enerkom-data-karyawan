package com.enerkom.karyawan.repository;

import com.enerkom.karyawan.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT u FROM users u where u.email = :email")
    public Optional<Users> getUserByEmai(@Param("email") String email);
}
