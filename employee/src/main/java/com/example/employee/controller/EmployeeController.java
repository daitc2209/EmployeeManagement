package com.example.employee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.OPTIONS,RequestMethod.PUT,RequestMethod.HEAD}
        ,allowCredentials = "false")

public class EmployeeController {
    @Autowired
    private EmployeeService empService;

    //Render data
    @GetMapping(value = "/employees")
    public List<Employee> getEmployee(){
        return empService.getEmployees();
    }

    //Create
    @PostMapping(value = "/employees")
    public Employee createEmployee(@RequestBody Employee emp){
        // ModelAttribute đóng vai trò là cầu lối giữa controller và View
        return empService.createEmp(emp);
    }


    //Update
    @GetMapping("/employees/edit/{id}")
    public Employee editEmpForm(@PathVariable(value = "id") Long id){
            return empService.getEmpById(id);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmp(@PathVariable(value = "id") Long id,
                              @RequestBody Employee emp){

        //lấy dữ liệu từ database từ id
        Employee existEmp = empService.getEmpById(id);
        if (existEmp.isDelete() == true )
        {
            throw new IllegalStateException("Employee doesn't exits");
        }
        else {
            existEmp.setId(id);
            existEmp.setFirstName(emp.getFirstName());
            existEmp.setLastName(emp.getLastName());
            existEmp.setemail_id(emp.getemail_id());
            existEmp.setDob(emp.getDob());
            existEmp.setAddress(emp.getAddress());

            return empService.updateEmp(existEmp);
        }
    }

    //Delete database
    @DeleteMapping("/employees/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value="id") Long id){
        empService.deleteEmp(id);
//        return new ResponseEntity<>("Xóa thành công", responseHeaders, HttpStatus.OK);
        return ResponseEntity.ok("Xóa dữ liệu thành công!");
    }
}
