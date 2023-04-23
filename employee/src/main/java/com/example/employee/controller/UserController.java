package com.example.employee.controller;


import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import com.example.employee.service.JwtService;
import com.example.employee.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
//@CrossOrigin("http://localhost:5173/","http://127.0.0.1:5173")
@CrossOrigin
//@CrossOrigin("http://127.0.0.1:5173")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/token")
    public String getToken(@RequestBody Users authRequest) throws Exception {
        // Get user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        System.out.println(userDetails);
        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
            // Generate token
            return jwtService.generateToken(authRequest.getEmail());
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

//    @PostMapping("/createUser")
//    public String createUser(@RequestBody Users req) throws Exception {
//        Users u =  userDetailsService.createUser(req);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getEmail());
//        System.out.println(userDetails.toString());
//        System.out.println(u.toString());
////        if(passwordEncoder.matches(u.getPassword(), userDetails.getPassword())){
////            // Generate token
////            return jwtService.generateToken(u.getEmail());
////        }
//        return jwtService.generateToken(u.getEmail());
////        throw new Exception("Email details invalid.");
//    }


//    @Autowired
//    private UserService userService;

//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute("user") Users user, HttpSession session){
//        Users userdata = userService.finduserByEmail(user);
//        if (user.getPassword().equals(userdata.getPassword()))
//        {
//            session.setAttribute("user", userdata);
//            session.setAttribute("login", true);
//            return "login_success";
//        }
//        else {
//            return "redirect:/?error";
////            return "error";
//        }
//    }

//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute("user") Users user, HttpSession session){
//        Users userdata = userService.finduserByEmail(user);
//        if (user.getPassword().equals(userdata.getPassword()))
//        {
//            session.setAttribute("user", userdata);
//            session.setAttribute("login", true);
//            return "login_success";
//        }
//        else {
//            return "redirect:/?error";
////            return "error";
//        }
//    }
//
//    @GetMapping("/")
//    public String Login(Model model){
//        Users user = new Users();
//        model.addAttribute("user",user);
//        return "Login";
//    }
//
//    @GetMapping("/register")
//    public String showSignUpForm(Model model){
//        Users user = new Users();
//        model.addAttribute("user", user);
//        return "SignUp";
//    }
//
//    @PostMapping("/registration")
//    public String Registrantion(Users user){
//        userService.createUser(user);
////        return "Registrantion_success";
//        return  "redirect:/register?success";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        session.setAttribute("login",null);
//        return "redirect:/";
//    }
}
