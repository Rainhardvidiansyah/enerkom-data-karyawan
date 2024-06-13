package com.enerkom.karyawan.entity;

import jakarta.persistence.*;
import lombok.ToString;



@Entity
@Table(name = "users")
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "users")
    private Employee employee;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
