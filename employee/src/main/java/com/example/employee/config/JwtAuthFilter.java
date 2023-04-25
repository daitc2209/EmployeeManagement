package com.example.employee.config;

import com.example.employee.service.JwtService;
import com.example.employee.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

     // Get token from request
     String token = getTokenFromRequest(request);   //trả về 1 chuỗi token

     // Validate token using JWT Service
     if(token != null && jwtService.validateToken(token)) {
         // Get username from token
         String username = jwtService.getUsernameFromToken(token);

         // Get user details
         UserDetails userDetails = userDetailsService.loadUserByUsername(username);

         // Create authentication object
         var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
         auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

         // Set user to spring context
         SecurityContextHolder.getContext()
               .setAuthentication(auth);
     }

     filterChain.doFilter(request, response);
}

// Lấy Token từ request
     private String getTokenFromRequest(HttpServletRequest request) {
     // Extract authentication header
             var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); //lấy Authentication trong request gửi đến
             // Bearer {JWT}

             // Check whether it starts with `Bearer ` or not
             if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
                  return authHeader.substring(7);
             }
             return null;
    }
}
