package com.example.employee.controller;
import com.example.employee.config.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import java.util.HashMap;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> getEmployee(){
        return empService.getEmployees();
    }

    //Search data
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> searchEmployees(@RequestParam String keyword) {
        return empService.SearchEmployees(keyword);
    }

    @PostMapping("/loginEmp")
    public ResponseEntity<Object> login(@RequestBody Employee emp) throws Exception{
        System.out.println("Email: "+ emp.getemail_id() + " password: " + emp.getPassword());
        return empService.login(emp.getemail_id(), emp.getPassword());
    }

    //Create
    @PostMapping(value = "/registerEmp")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee emp){
        // ModelAttribute đóng vai trò là cầu lối giữa controller và View
        return empService.createEmp(emp);
    }


    //Update
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> editEmpForm(@PathVariable(value = "id") Long id){
        if (empService.getEmpById(id) == null)
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);
        }
        return ResponseHandler.responseBuilder("GET_EMP_BY_ID_SUCCESS", HttpStatus.OK, empService.getEmpById(id),00);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> updateEmp(@PathVariable(value = "id") Long id,
                              @RequestBody Employee emp){
        Map map = new HashMap<>();
        //lấy dữ liệu từ database từ id
        Employee existEmp = empService.getEmpById(id);
        if (existEmp.isDelete() == true || existEmp == null)
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);
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

            empService.updateEmp(existEmp);

            return ResponseHandler.responseBuilder("EDIT_SUCCESS", HttpStatus.OK, "",00);
        }
    }

    //Delete database
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value="id") Long id){
        if (empService.getEmpById(id) == null)
        {
            return ResponseHandler.responseBuilder("NOT_FOUND_ID", HttpStatus.NOT_FOUND, "",04);
        }
        empService.deleteEmp(id);
        return ResponseHandler.responseBuilder("DELETE_SUCCESS", HttpStatus.OK, "",00);
    }
}
