package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepository;   //tương tác với database

    //READ
//    public List<Employee> getEmployees(String keyword) {
//        if(keyword != null)
//            return empRepository.findAll(keyword);
//        return empRepository.findAll();
//    }

    public List<Employee> getEmployees() {
        return empRepository.findEmployeeByEmailNotIsDelete();
    }

    //CREATE
//    public void createEmp(Employee emp){
//        Optional<Employee> empOptional = empRepository.findEmployeeByEmail(emp.getEmailId());
//        if (empOptional.isPresent()){   //báo xem có tồn tại email trùng ko
//            throw new IllegalStateException("email taken");
//        }
//        empRepository.save(emp);
//    }
    public Employee createEmp(Employee emp){
        Optional<Employee> empOptional = empRepository.findEmployeeByEmail(emp.getemail_id());
        if (empOptional.isPresent()){   //báo xem có tồn tại email trùng ko
            throw new IllegalStateException("email taken");
        }
        Employee employee = new Employee(
                emp.getFirstName(),emp.getLastName(),emp.getemail_id(),emp.getDob(),emp.getAddress(),emp.isDelete()
        );
        System.out.println(employee.toString());
//        return empRepository.save(emp);
        return empRepository.save(employee);
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
        return empRepository.findById(id).get();
    }

    public Employee updateEmp(Employee emp){
            return empRepository.save(emp);
    }

}