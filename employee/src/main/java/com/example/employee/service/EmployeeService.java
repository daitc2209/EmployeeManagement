package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepository;   //tương tác với database

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<Employee> SearchEmployees(String keyword) {
            return empRepository.findAll(keyword);
    }

    //READ
    public List<Employee> getEmployees(/*String keyword*/) {
//        if (keyword != null && keyword != "")
//            return empRepository.findAll(keyword);
        return empRepository.findEmployeeByEmailNotIsDelete();
    }

    //Login
    public String login(String email, String password) throws Exception {
        Employee emp = empRepository.findEmployeeByEmail1(email);
        if (emp == null){   //báo xem có tồn tại email trùng ko
            throw new IllegalStateException("email not exist");
        }
        if(emp.isActive()) {
            if (passwordEncoder.matches(password, emp.getPassword())) {
                return jwtService.generateToken(emp.getemail_id());
            }
            throw new Exception("Email details invalid.");
        }
        throw new Exception("Email has disactived.");

    }

    //CREATE
    public Employee createEmp(Employee emp){
        Optional<Employee> empOptional = empRepository.findEmployeeByEmail(emp.getemail_id());
        if (empOptional.isPresent()){   //báo xem có tồn tại email trùng ko
            throw new IllegalStateException("email taken");
        }
        emp.setDelete(false);
        emp.setActive(true);
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        System.out.println(emp.toString());
        return empRepository.save(emp);
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
        return empRepository.findById(id).get();
    }

    public Employee updateEmp(Employee emp){
            return empRepository.save(emp);
    }

}