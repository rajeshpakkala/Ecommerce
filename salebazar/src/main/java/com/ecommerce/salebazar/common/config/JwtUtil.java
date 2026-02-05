package com.ecommerce.salebazar.common.config;

import com.ecommerce.salebazar.exception.UnauthorizedException;
import com.ecommerce.salebazar.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    private static final String JWT_SECRET =
            "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_32_CHARS_MIN";
    private static final long JWT_EXPIRATION_MS = 86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    public String generateToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName().name()) // ROLE_ADMIN
                .toList();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT VALIDATION ERROR = " + e.getClass().getName());
            System.out.println("JWT MESSAGE = " + e.getMessage());
            throw new UnauthorizedException("Invalid JWT token");
        }
    }


    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 🔥 FIXED
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}
