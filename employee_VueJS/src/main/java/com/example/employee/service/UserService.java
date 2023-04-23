//package com.example.employee.service;
//
//import com.example.employee.model.Users;
//import com.example.employee.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRep;
//
//    public void createUser(Users user){
//        Optional<Users> userOptional = userRep.findUserByEmail(user.getEmail());
//        if (userOptional.isPresent()){   //báo xem có tồn tại email trùng ko
//            throw new IllegalStateException("email taken");
//        }
//        userRep.save(user);
//    }
//
//    public Users finduserByEmail(Users user){
//        return userRep.findUserByEmail1(user.getEmail());
//    }
//
//
//}
