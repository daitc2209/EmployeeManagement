package com.example.employee.service;

//import com.example.employee.model.MUserDetail;
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
            map.put("message","EMAIL_EXIST");
            map.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole() == "")
            user.setRole("ROLE_USER");
        // Save user
        userRep.save(user);
        map.put("message","CREATE_SUCCESS");
        map.put("responseCode","1");
        map.put("data","");
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<Object> login(String email, String password) throws Exception {
        Optional<Users> user = userRep.findUserByEmail(email);
        Map<String,String> m = new HashMap<>();
        System.out.println(user);
        if(!user.isPresent())
        {
            m.put("message","EMAIL_NOT_EXIST");
            m.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }
        var u = user.get();
        if(passwordEncoder.matches(password, u.getPassword())) {
            if (u.isActive()) {
                m.put("token",jwtService.generateToken(u.getEmail()));
                m.put("role", jwtService.getRoleFromToken(jwtService.generateToken(u.getEmail())));
                m.put("responseCode","1");
                return ResponseEntity.ok(m);
            }
            else {
                m.put("message","EMAIL_NOT_ACTIVE");
                m.put("responseCode","0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
            }
        }
        else
        {
            m.put("message","PASSWORD_NOT_CORRECT");
            m.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }

    }

    public ResponseEntity<Object> getUser(){
        Map map = new HashMap<>();
        map.put("message","GET_LIST_SUCCESS");
        map.put("responseCode","1");
        map.put("data",userRep.findAll());
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<Object> SearchUser(String keyword) {
        Map map = new HashMap<>();
        map.put("message","SEARCH_SUCCESS");
        map.put("responseCode","1");
        map.put("data",userRep.findAll(keyword));
        return ResponseEntity.ok(map);
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
