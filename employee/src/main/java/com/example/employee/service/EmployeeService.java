package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
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
        Map map = new HashMap<>();
        map.put("message","SEARCH_SUCCESS");
        map.put("responseCode","1");
        map.put("data",empRepository.findAll(keyword));
        return ResponseEntity.ok(map);
    }

    //READ
    public ResponseEntity<Object> getEmployees() {
        Map map = new HashMap<>();
        map.put("message","GET_LIST_SUCCESS");
        map.put("responseCode","1");
        map.put("data",empRepository.findEmployeeByEmailNotIsDelete());
        return ResponseEntity.ok(map);
    }

    //Login
    public ResponseEntity<Object> login(String email, String password) throws Exception {
        Employee emp = empRepository.findEmployeeByEmail1(email);
        Map<String,String> m = new HashMap<>();
        if (emp == null){   //báo xem có tồn tại email trùng ko
            m.put("message","EMAIL_NOT_EXIST");
            m.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }
        if(emp.isActive()) {
            if (passwordEncoder.matches(password, emp.getPassword())) {
                m.put("responseCode","1");
                m.put("token",jwtService.generateToken(emp.getemail_id()));
                return ResponseEntity.ok(m);
            }
            else {
                m.put("message","PASSWORD_NOT_CORRECT");
                m.put("responseCode","0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
            }
        }
        else
        {
            m.put("message","EMAIL_NOT_ACTIVE");
            m.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }

    }

    //CREATE
    public ResponseEntity<Object> createEmp(Employee emp){
        Map<String,String> map = new HashMap<>();

        Optional<Employee> empOptional = empRepository.findEmployeeByEmail(emp.getemail_id());
        if (empOptional.isPresent()){   //báo xem có tồn tại email trùng ko
            map.put("message","EMAIL_EXIST");
            map.put("responseCode","0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        emp.setDelete(false);
        emp.setActive(true);
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        System.out.println(emp.toString());
        empRepository.save(emp);

        map.put("responseCode","1");
        map.put("data","");
        map.put("message","REGISTER_SUCCESS");
        return ResponseEntity.ok(map);
    }

    //DELETE
    public void deleteEmp(Long empId) {
        boolean exists = empRepository.existsById(empId);
        if (!exists) {
            throw new IllegalStateException("employee with id " + empId + " doesn't exists");
        }
        Employee emp = empRepository.findUserById(empId);
        emp.setActive(false);
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