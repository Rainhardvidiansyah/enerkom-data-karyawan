package com.enerkom.karyawan.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);


    @Value("${jwt.secret.key}")
    private String key;

    @Value("${jwt.expiration.date}")
    private Long jwtExpiration;

    @Value("${refresh.token.expiry.date}")
    private Long refreshTokenExpiration;



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    public String getJwtTokenFromRequest(HttpServletRequest request, String cookieName){
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if(cookie != null){
            return cookie.getValue();
        }else{
            return null;
        }
    }

    public String generateAccessTokenFromRefreshToken(String token){
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        return buildToken(new HashMap<>(), username, refreshTokenExpiration);

    }
    public boolean isRefreshTokenValid(String token){
        return !isTokenExpired(token);
    }


    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expiration) {
        return buildToken(extraClaims, userDetails.getUsername(), expiration);
    }

    private String buildToken(Map<String, Object> extraClaim, String userName, Long expiration){
        return
                Jwts.builder()
                        .setClaims(extraClaim)
                        .setSubject(userName)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                        .compact();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }



    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        log.info("Is token valid: {}", isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("Invalid JWT Token", e);
            return null;
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
