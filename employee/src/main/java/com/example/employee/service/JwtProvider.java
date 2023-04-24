package com.example.employee.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtProvider {

    //private static final String SECRET_KEY = "2948404D6251655468576D5A7134743777217A25432A462D4A614E645266556A";
    private String jwtSecret = "sIoVC8OFOgmxbk9XRYtY2zMKXuYXBGL2d3x1IV37";

    private Claims parseToken(String token) {
        // Create JwtParser
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build();

        try {
            return jwtParser.parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println(e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println(e.getMessage());
        } catch (SignatureException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean validateToken(String token) {
        return parseToken(token) != null;
    }

    public String getUsernameFromToken(String token) {
        // Get claims
        Claims claims = parseToken(token);

        // Extract subject
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public String generateToken(String email) {
        // Create signing key
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Generate token
        var currentDate = new Date(System.currentTimeMillis() + (1 * 60 * 10000));
        var expiration = new Date(System.currentTimeMillis() + (100 * 60 * 10000));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}