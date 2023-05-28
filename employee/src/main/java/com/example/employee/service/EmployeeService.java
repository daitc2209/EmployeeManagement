package com.example.employee.service;

import com.example.employee.config.ResponseHandler;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepository;   //tương tác với database

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<Object> SearchEmployees(String keyword) {
        return ResponseHandler.responseBuilder("SEARCH_SUCCESS", HttpStatus.OK, empRepository.findAll(keyword),00);
    }

    //READ
    public ResponseEntity<Object> getEmployees() {
        return ResponseHandler.responseBuilder("GET_LIST_SUCCESS", HttpStatus.OK, empRepository.findEmployeeByEmailNotIsDelete(),00);
    }

    //Login
    public ResponseEntity<Object> login(String email, String password) throws Exception {
        Employee emp = empRepository.findEmployeeByEmail1(email);
        Map<String,String> m = new HashMap<>();
        if (emp == null){   //báo xem có tồn tại email trùng ko
            return ResponseHandler.responseBuilder("EMAIL_NOT_EXIST", HttpStatus.BAD_REQUEST, "",01);
        }
        if(emp.isActive()) {
            if (passwordEncoder.matches(password, emp.getPassword())) {
                m.put("token",jwtService.generateToken(emp.getemail_id()));
                return ResponseHandler.responseBuilder("Login_Success", HttpStatus.OK, m,00);
            }
            else {
                return ResponseHandler.responseBuilder("PASSWORD_NOT_CORRECT", HttpStatus.BAD_REQUEST, "",02);
            }
        }
        else
        {
            return ResponseHandler.responseBuilder("EMAIL_NOT_ACTIVE", HttpStatus.BAD_REQUEST, "",03);
        }

    }

    //CREATE
    public ResponseEntity<Object> createEmp(Employee emp){
        Map<String,String> map = new HashMap<>();

        Optional<Employee> empOptional = empRepository.findEmployeeByEmail(emp.getemail_id());
        if (empOptional.isPresent()){   //báo xem có tồn tại email trùng ko
            return ResponseHandler.responseBuilder("EMAIL_NOT_EXIST", HttpStatus.BAD_REQUEST, "",01);
        }
        emp.setDelete(false);
        emp.setActive(true);
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        System.out.println(emp.toString());
        empRepository.save(emp);

        return ResponseHandler.responseBuilder("REGISTER_SUCCESS", HttpStatus.OK, "",00);
    }

    //DELETE
    public void deleteEmp(Long empId) {
        boolean exists = empRepository.existsById(empId);
        if (!exists) {
            throw new IllegalStateException("employee with id " + empId + " doesn't exists");
        }
        Employee emp = empRepository.findUserById(empId);
        emp.setDelete(true);
        empRepository.save(emp);
    }

    //UPDATE
    public Employee getEmpById(Long id)
    {
        if (empRepository.findById(id).isPresent())
            return empRepository.findById(id).get();
        return null;
    }

    public Employee updateEmp(Employee emp){
            return empRepository.save(emp);
    }

}