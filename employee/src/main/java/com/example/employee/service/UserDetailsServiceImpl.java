package com.example.employee.service;

//import com.example.employee.model.MUserDetail;
import com.example.employee.model.Employee;
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
import org.springframework.web.server.ResponseStatusException;

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

    public Users createUser(Users user) throws Exception {
        // Check whether username exists or not
        Optional<Users> userOptional = userRep.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole() == "")
            user.setRole("ROLE_USER");
        // Save user
        return userRep.save(user);
    }

    public ResponseEntity<Map<String, String>> login(String email, String password) throws Exception {
        var user = userRep.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email not found"));
        System.out.println(user);
        Map<String,String> m = new HashMap<>();
        m.put("role", user.getRole());
        if(user.isActive()) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                m.put("token",jwtService.generateToken(user.getEmail()));
                return ResponseEntity.ok(m);
            }
            throw new Exception("Email details invalid.");
        }
        throw new Exception("Email has disactived.");

    }

    public List<Users> getUser(){
                    return userRep.findAll();
                }

    public List<Users> SearchUser(String keyword) {
        return userRep.findAll(keyword);
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
