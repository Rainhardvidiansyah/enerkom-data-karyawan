package com.enerkom.karyawan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enerkom")
public class PublicApi {

    @GetMapping("/public")
    public String showPublicApi() {
        return "Ini adalah endpoint public";
    }
}
