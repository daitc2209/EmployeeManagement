package com.example.employee.controller;

import com.example.employee.config.ResponseHandler;
import com.example.employee.model.Users;
import com.example.employee.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //Search data
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> searchEmployees(@RequestParam String keyword) {
        return userDetailsService.SearchUser(keyword);
    }

    //CREATE
    @PostMapping("/createUser")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> getUser(){
        return userDetailsService.getUser();
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="id") Long id){
        if (userDetailsService.getUserById(id) == null)
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);

        }
        userDetailsService.deleteUser(id);
        return ResponseHandler.responseBuilder("DELETE_SUCCESS", HttpStatus.OK, "",00);
    }

    //UPDATE
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> editUserForm(@PathVariable(value = "id") Long id){
        Map map = new HashMap<>();
        if (userDetailsService.getUserById(id) == null)
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);
        }
        return ResponseHandler.responseBuilder("GET_EMP_BY_ID_SUCCESS", HttpStatus.OK, userDetailsService.getUserById(id),00);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id,
                              @RequestBody Users u){

        //lấy dữ liệu từ database từ id
        Users existUser = userDetailsService.getUserById(id);

        if (existUser.isDelete() == true || existUser == null )
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);

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
            return ResponseHandler.responseBuilder("EDIT_SUCCESS", HttpStatus.OK, "",00);
        }
    }
}
