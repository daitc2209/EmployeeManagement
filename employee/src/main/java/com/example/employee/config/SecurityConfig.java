package com.example.employee.config;

import com.example.employee.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.employee.service.JwtAuthEntryPoint;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CORS
        http.cors();

        // Disable CSRF
        http.csrf().disable();

        // Change session management to STATELESS
        http.sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add exception handler
        http.exceptionHandling()
                  .authenticationEntryPoint(jwtAuthEntryPoint);

        // Authorize http requests
        http.authorizeHttpRequests()
                .requestMatchers("/api/user/login","/api/employees/loginEmp").permitAll()
                .requestMatchers("/api/employees/registerEmp").permitAll()
//                .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
//                .requestMatchers("/api/employees/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN")
                  .anyRequest().authenticated();

        // Add JWT authentication filter
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
