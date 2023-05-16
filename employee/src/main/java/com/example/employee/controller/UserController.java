package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import com.example.employee.service.JwtService;
import com.example.employee.service.UserDetailsServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //Search data
    @GetMapping("/search")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> searchEmployees(@RequestParam String keyword) {
        return userDetailsService.SearchUser(keyword);
    }

    //CREATE
    @PostMapping("/createUser")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody Users req) throws Exception {
        System.out.println(req.toString());
        return userDetailsService.createUser(req);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users users) throws Exception{
        return userDetailsService.login(users.getEmail(), users.getPassword());
    }

    //READ
    @GetMapping(value = "/getuser")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> getUser(){
        return userDetailsService.getUser();
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") Long id){
        if (userDetailsService.getUserById(id) == null)
        {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("error", "NOT_FOUND_ID");
            errorMap.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
        }
        userDetailsService.deleteUser(id);
        Map<String,String> map = new HashMap<>();
        map.put("responseCode", "1");
        map.put("message","DELETE_SUCCESS");
        return ResponseEntity.ok(map);
    }

    //UPDATE
    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> editUserForm(@PathVariable(value = "id") Long id){
        Map map = new HashMap<>();
        if (userDetailsService.getUserById(id) == null)
        {
            map.put("error", "NOT_FOUND_ID");
            map.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        map.put("responseCode","1");
        map.put("data",userDetailsService.getUserById(id));
        map.put("message","GET_USER_BY_ID_SUCCESS");
        return  ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id,
                              @RequestBody Users u){

        //lấy dữ liệu từ database từ id
        Users existUser = userDetailsService.getUserById(id);
        Map<String,String> Map = new HashMap<>();

        if (existUser.isDelete() == true || existUser == null )
        {
            Map.put("error", "NOT_FOUND_ID");
            Map.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map);
        }
        else {
            if(u.getName() != null && u.getName() != "")
                existUser.setName(u.getName());
            if(u.getEmail() != null && u.getEmail() != "")
                existUser.setEmail(u.getEmail());
            if(u.getRole() != null && u.getRole() != "")
                existUser.setRole(u.getRole());
            existUser.setActive(u.isActive());

            userDetailsService.updateUser(existUser);
            Map.put("responseCode","1");
            Map.put("data","");
            Map.put("message","EDIT_SUCCESS");

            return ResponseEntity.ok(Map);
        }
    }
}
