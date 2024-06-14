package com.enerkom.karyawan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enerkom/admin")
public class AdminController {


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() + " adalah " + authentication.getAuthorities();

    }
}
