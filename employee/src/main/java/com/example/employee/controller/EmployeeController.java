package com.example.employee.controller;

import com.example.employee.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.OPTIONS,RequestMethod.PUT,RequestMethod.HEAD}
        ,allowCredentials = "false")

public class EmployeeController {
    @Autowired
    private EmployeeService empService;

    //Render data
    @GetMapping("/getEmp")
    public List<Employee> getEmployee(){
        return empService.getEmployees();
    }

    //Search data
    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return empService.SearchEmployees(keyword);
    }

    @PostMapping("/loginEmp")
    public ResponseEntity<Map<String, String>> login(@RequestBody Employee emp) throws Exception{
        System.out.println("Email: "+ emp.getemail_id() + " password: " + emp.getPassword());
        return empService.login(emp.getemail_id(), emp.getPassword());
    }

    //Create
    @PostMapping(value = "/registerEmp")
    public Employee createEmployee(@RequestBody Employee emp){
        // ModelAttribute đóng vai trò là cầu lối giữa controller và View
        return empService.createEmp(emp);
    }


    //Update
    @GetMapping("/edit/{id}")
    public Employee editEmpForm(@PathVariable(value = "id") Long id){
            return empService.getEmpById(id);
    }

    @PutMapping("/{id}")
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
            if (emp.getFirstName() != null && emp.getFirstName() != "")
                existEmp.setFirstName(emp.getFirstName());
            if (emp.getLastName() != null && emp.getLastName() != "")
                existEmp.setLastName(emp.getLastName());
            if (emp.getemail_id() != null && emp.getemail_id() != "")
                existEmp.setemail_id(emp.getemail_id());
            if (emp.getDob() != null && emp.getDob().toString() != "")
                existEmp.setDob(emp.getDob());
            if (emp.getAddress() != null && emp.getAddress() != "")
                existEmp.setAddress(emp.getAddress());

            return empService.updateEmp(existEmp);
        }
    }

    //Delete database
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteEmployee(@PathVariable(value="id") Long id){
        empService.deleteEmp(id);
//        return new ResponseEntity<>("Xóa thành công", responseHeaders, HttpStatus.OK);
        Map<String,String> map = new HashMap<>();
        map.put("message","Xoa thanh cong");
        return ResponseEntity.ok(map);
    }
}
