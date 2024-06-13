package com.enerkom.karyawan.entity;


import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles")
@ToString
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "role_name")
    private String roleName;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    public Roles(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }


}
