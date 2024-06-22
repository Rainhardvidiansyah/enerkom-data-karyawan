package com.enerkom.karyawan.dto;

import java.util.List;

public class AuthResponseDto {

    private String email;
    private String token;

    private String refreshToken;

    private List<String> roles;

    public String getEmail() {
        return email;
    }

    public AuthResponseDto(String email, String token, String refreshToken, List<String> roles) {
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
