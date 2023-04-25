package com.example.employee.service;

//import com.example.employee.model.MUserDetail;
import com.example.employee.model.Users;
import com.example.employee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRep.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

//        var authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new User(user.getEmail(), user.getPassword(),new ArrayList<>());
    }

    public Users createUser(Users user) throws Exception {
        // Check whether username exists or not
        Optional<Users> userOptional = userRep.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(true);
        // Save user
        return userRep.save(user);
    }

    public String login(String email, String password) throws Exception {
//  Users user = userRep.findUserByEmail1(email);
        var user = userRep.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not found"));

        System.out.println(user);
        if(user.isActive()) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtService.generateToken(user.getEmail());
            }
            throw new Exception("Email details invalid.");
        }
        throw new Exception("Email has disactived.");

    }

    public List<Users> getUserActive(){
                    return userRep.findAll();
                }
}
