package com.example.employee.service;

import com.example.employee.config.ResponseHandler;
import com.example.employee.model.Users;
import com.example.employee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        System.out.println("Author: " + authorities );
        return new User(user.getEmail(), user.getPassword(),authorities);
    }

    public ResponseEntity<Object> createUser(Users user) throws Exception {
        Map map = new HashMap<>();
        // Check whether username exists or not
        Optional<Users> userOptional = userRep.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            return ResponseHandler.responseBuilder("EMAIL_NOT_EXIST", HttpStatus.BAD_REQUEST, "",01);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole() == "")
            user.setRole("ROLE_USER");
        // Save user
        userRep.save(user);
        return ResponseHandler.responseBuilder("CREATE_SUCCESS", HttpStatus.OK, "",00);
    }

    public ResponseEntity<Object> login(String email, String password) throws Exception {
        Optional<Users> user = userRep.findUserByEmail(email);
        Map<String,String> m = new HashMap<>();
        System.out.println(user);
        if(!user.isPresent())
        {
            return ResponseHandler.responseBuilder("EMAIL_NOT_EXIST", HttpStatus.BAD_REQUEST, "",01);
        }
        var u = user.get();
        if(passwordEncoder.matches(password, u.getPassword())) {
            if (u.isActive()) {
                m.put("token",jwtService.generateToken(u.getEmail()));
                m.put("role", jwtService.getRoleFromToken(jwtService.generateToken(u.getEmail())));
                return ResponseHandler.responseBuilder("Login_Success", HttpStatus.OK, m,00);
            }
            else {
                return ResponseHandler.responseBuilder("EMAIL_NOT_ACTIVE", HttpStatus.BAD_REQUEST, "",03);
            }
        }
        else
        {
            return ResponseHandler.responseBuilder("PASSWORD_NOT_CORRECT", HttpStatus.BAD_REQUEST, "",02);
        }

    }

    public ResponseEntity<Object> getUser(){
        return ResponseHandler.responseBuilder("GET_LIST_SUCCESS", HttpStatus.OK, userRep.findAll(),00);

    }

    public ResponseEntity<Object> SearchUser(String keyword) {
        return ResponseHandler.responseBuilder("SEARCH_SUCCESS", HttpStatus.OK, userRep.findAll(keyword),00);

    }

    public void deleteUser(Long id) {
        boolean exists = userRep.existsById(id);
        if (!exists) {
            throw new IllegalStateException("employee with id " + id + " doesn't exists");
        }
        var u = userRep.findById(id).get();
        u.setActive(false);
        u.setDelete(true);
        userRep.save(u);
    }


    public Users getUserById(Long id)
    {
        if (userRep.findById(id).isPresent())
            return userRep.findById(id).get();
        return null;
    }

    public Users updateUser(Users u){
        return userRep.save(u);
    }
}
