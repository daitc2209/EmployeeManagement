package com.example.employee.controller;
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
        Map map = new HashMap<>();
        if (empService.getEmpById(id) == null)
        {
            map.put("error", "NOT_FOUND_ID");
            map.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        map.put("responseCode","1");
        map.put("data",empService.getEmpById(id));
        map.put("message","GET_EMP_BY_ID_SUCCESS");
        return  ResponseEntity.ok(map);
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
            map.put("error", "NOT_FOUND_ID");
            map.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
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

            map.put("responseCode","1");
            map.put("data","");
            map.put("message","EDIT_SUCCESS");
            return ResponseEntity.ok(map);
        }
    }

    //Delete database
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value="id") Long id){
        if (empService.getEmpById(id) == null)
        {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("error", "NOT_FOUND_ID");
            errorMap.put("responseCode", "0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
        }
        empService.deleteEmp(id);
        Map<String,String> map = new HashMap<>();
        map.put("responseCode", "1");
        map.put("message","DELETE_SUCCESS");
        return ResponseEntity.ok(map);
    }
}
