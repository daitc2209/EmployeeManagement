package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    //private static final String SECRET_KEY = "2948404D6251655468576D5A7134743777217A25432A462D4A614E645266556A";
    private String jwtSecret = "sIoVC8OFOgmxbk9XRYtY2zMKXuYXBGL2d3x1IV37";

    @Autowired
    private UserRepository userRep;

    private Claims parseToken(String token) {   //giải mã chuỗi Token
        // Create JwtParser
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build();

        try {
            return jwtParser.parseClaimsJws(token)
                    .getBody(); //nếu không có lỗi, nó sẽ trả về
            // đối tượng chứa các thông tin về người dùng được giải mã từ token

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

        return null;    //nếu có lỗi, nó sẽ trả về null
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

    public String getRoleFromToken(String token) {
        // Get claims
        Claims claims = parseToken(token);

        // Extract subject
        if (claims != null) {
            return claims.get("role", String.class);
        }
        return null;
    }

    public String generateToken(String email) {

        Optional<Users> user = userRep.findUserByEmail(email);

        // Create signing key
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Generate token
        var currentDate = new Date(System.currentTimeMillis());
//        var BeforeTime = new Date(System.currentTimeMillis() + (1 * 60 * 10000));
        var expiration = new Date(System.currentTimeMillis() + (1000 * 60 * 10000)); // hết hạn sau khoảng 1 ngày

        if (user.isPresent()) {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role", user.get().getRole())
                    .setIssuedAt(currentDate)
                    .setExpiration(expiration)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(currentDate)
                    .setExpiration(expiration)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }
    }
}