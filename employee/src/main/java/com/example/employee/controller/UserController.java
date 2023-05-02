package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import com.example.employee.service.JwtService;
import com.example.employee.service.UserDetailsServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @GetMapping("/token")
//    public String getToken(@RequestBody Users authRequest) throws Exception {
//        // Get user details
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
//        System.out.println(userDetails);
//        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
//            // Generate token
//            return jwtService.generateToken(authRequest.getEmail());
//        }
//
//        throw new Exception("Email details invalid.");
//    }

    //CREATE
    @PostMapping("/createUser")
    public Users createUser(@RequestBody Users req) throws Exception {
        System.out.println(req.toString());
        return userDetailsService.createUser(req);
    }

    //LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Users users) throws Exception{
        return userDetailsService.login(users.getEmail(), users.getPassword());
    }

    //READ
    @GetMapping(value = "/userActive")
    public List<Users> getUserActive(){
        return userDetailsService.getUserActive();
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable(value="email") String email){
        Users user =  userDetailsService.getUserByEmail(email);
        return ResponseEntity.ok(user.getRole());
//        var user =  userDetailsService.getUserByEmail(u.getEmail());
//        return  user.getRole();
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value="id") Long id){
        userDetailsService.deleteUser(id);
//        return new ResponseEntity<>("Xóa thành công", responseHeaders, HttpStatus.OK);
        return ResponseEntity.ok("Xóa dữ liệu thành công!");
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
            existUser.setName(u.getName());
            existUser.setEmail(u.getEmail());
            existUser.setRole(u.getRole());
            existUser.setActive(u.isActive());

            return userDetailsService.updateUser(existUser);
        }
    }
}
