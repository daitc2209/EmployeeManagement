package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import com.example.employee.service.JwtService;
import com.example.employee.service.UserDetailsServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Users> searchEmployees(@RequestParam String keyword) {
        return userDetailsService.SearchUser(keyword);
    }

    //CREATE
    @PostMapping("/createUser")
    public Users createUser(@RequestBody Users req) throws Exception {
        System.out.println(req.toString());
        return userDetailsService.createUser(req);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users users) throws Exception{
        return userDetailsService.login(users.getEmail(), users.getPassword());
    }

    //READ
    @GetMapping(value = "/getuser")
    public List<Users> getUser(){
        return userDetailsService.getUser();
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable(value="id") Long id){
        if (userDetailsService.getUserById(id) == null)
        {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("error", "Không tìm thấy id để xóa!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
        }
        userDetailsService.deleteUser(id);
        Map<String,String> map = new HashMap<>();
        map.put("message","Xóa dữ liệu thành công!");
        return ResponseEntity.ok(map);
    }

    //UPDATE
    @GetMapping("/edit/{id}")
    public Users editUserForm(@PathVariable(value = "id") Long id){
        return userDetailsService.getUserById(id);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable(value = "id") Long id,
                              @RequestBody Users u){

        //lấy dữ liệu từ database từ id
        Users existUser = userDetailsService.getUserById(id);
        if (existUser.isDelete() == true )
        {
            throw new IllegalStateException("User doesn't exits");
        }
        else {
            if(u.getName() != null && u.getName() != "")
                existUser.setName(u.getName());
            if(u.getEmail() != null && u.getEmail() != "")
                existUser.setEmail(u.getEmail());
            if(u.getRole() != null && u.getRole() != "")
                existUser.setRole(u.getRole());
            existUser.setActive(u.isActive());

            return userDetailsService.updateUser(existUser);
        }
    }
}
