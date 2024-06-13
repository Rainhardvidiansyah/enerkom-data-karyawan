package com.enerkom.karyawan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enerkom/protected")
public class ProtectedApi {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getProtectedApi(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "halo " + authentication.getName();
    }
}
