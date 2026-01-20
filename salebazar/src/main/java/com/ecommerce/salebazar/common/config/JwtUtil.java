package com.ecommerce.salebazar.common.config;

import com.ecommerce.salebazar.exception.UnauthorizedException;
import com.ecommerce.salebazar.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public class JwtUtil {

    private final String jwtSecret =
            "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_32_CHARS_MIN";
    private final long jwtExpirationMs = 86400000;

    public String generateToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .toList();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid JWT token");
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
