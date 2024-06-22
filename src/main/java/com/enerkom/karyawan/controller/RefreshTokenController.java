package com.enerkom.karyawan.controller;

import com.enerkom.karyawan.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enerkom")
public class RefreshTokenController {

    private static final Logger log = LoggerFactory.getLogger(RefreshTokenController.class);

    @Autowired
    private JwtService jwtService;


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies){
                if("refreshToken".equals(cookie.getName())){
                    String refreshToken = cookie.getValue();
                    log.info("refresh token dalam metode refresh token: {}", refreshToken);
                    log.info("value cookie dalam metode refresh token: {}", cookie.getValue());
                    boolean isValid = jwtService.isRefreshTokenValid(refreshToken);
                    if(!isValid){
                        return new ResponseEntity<>("Refresh Token Kadaluarsa", HttpStatus.UNAUTHORIZED);
                    }else{
                        String newAccessToken = jwtService.generateAccessTokenFromRefreshToken(refreshToken);
                        return new ResponseEntity<>("Akses token yang baru: " + newAccessToken, HttpStatus.OK);
                    }
                }else{
                    return new ResponseEntity<>("Token salah", HttpStatus.valueOf(401));
                }
            }
        }
        return new ResponseEntity<>("ada yang salah di sini", HttpStatus.UNAUTHORIZED);
    }
}
