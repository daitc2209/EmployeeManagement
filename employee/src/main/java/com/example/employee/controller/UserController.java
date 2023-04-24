package com.example.employee.controller;

import com.example.employee.model.Users;
import com.example.employee.service.JwtProvider;
import com.example.employee.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/token")
    public String getToken(@RequestBody Users authRequest) throws Exception {
        // Get user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        System.out.println(userDetails);
        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
            // Generate token
            return jwtProvider.generateToken(authRequest.getEmail());
        }

        throw new Exception("Email details invalid.");
    }

    @PostMapping("/createUser")
    public Users createUser(@RequestBody Users req) throws Exception {
        System.out.println(req.toString());
        return userDetailsService.createUser(req);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users) throws Exception{
        return userDetailsService.login(users.getEmail(), users.getPassword());

    }

    @GetMapping(value = "/userActive")
    public List<Users> getUserActive(){
        return userDetailsService.getUserActive();
    }

}
