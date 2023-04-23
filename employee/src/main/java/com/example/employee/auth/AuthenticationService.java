//package com.example.employee.auth;
//
//import com.example.employee.model.Users;
//import com.example.employee.repository.UserRepository;
//import com.example.employee.service.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationService {
//
//    private final UserRepository repository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    public AuthenticaionResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .username(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .roles(request.getRole())
//                .build();
//        Users nuser = new Users(request.getEmail(),passwordEncoder.encode(request.getPassword()),request.getRole());
//        repository.save(nuser);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticaionResponse.builder().token(jwtToken).build();
//    }
//
//    public AuthenticaionResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        Users users = repository.findUserByEmail(request.getEmail()).orElseThrow();
//        var user = User.builder()
//                .username(users.getEmail())
//                .password(passwordEncoder.encode(users.getPassword()))
//                .roles(users.getRole())
//                .build();
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticaionResponse.builder().token(jwtToken).build();
//    }
//}
