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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/")
//@CrossOrigin("http://localhost:5173/")
//@CrossOrigin(origins = "http://localhost:5173")
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
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Access-Control-Allow-Origin", "*");
        empService.deleteEmp(id);
//        return new ResponseEntity<>("Xóa thành công", responseHeaders, HttpStatus.OK);
        return ResponseEntity.ok("Xóa dữ liệu thành công!");
    }

//    @GetMapping(value = "/employees")
//    public String viewHomePage(Model model,@Param("keyword") String keyword, HttpSession session){
//        if(session.getAttribute("login") != null && (boolean) session.getAttribute("login"))
//        {
//            List<Employee> epmList = empService.getEmployees(keyword);
//            model.addAttribute("listEmps",epmList);
//            model.addAttribute("keyword",keyword);
//            return "index";
//        }
//        return "redirect:/";
//    }



//
//    //Create database
//    @GetMapping("/employees/new")
//    public String showEmpForm(Model model, HttpSession session)
//    {
//        if (session.getAttribute("login") != null && (boolean) session.getAttribute("login"))
//        {
//            //tạo 1 emp để giữ dữ liệu được truyền vào
//            Employee emp = new Employee();
//            model.addAttribute("employees", emp);   //là object bên trang createEmp
//            return "createEmp";
//        }
//        else  return "redirect:/";
//    }
//
//    @PostMapping(value = "/employees")
//    public String createEmployee(@ModelAttribute("employees") Employee emp){
//        // ModelAttribute đóng vai trò là cầu lối giữa controller và View
//        empService.createEmp(emp);
//        return "redirect:/employees?success";
//    }
//
//
//
//    //Update database
////    @PutMapping(value = "/employees/{empId}")
////    public Employee getEmployee(@PathVariable(value = "empId") Long id,
////                                @RequestBody Employee empDetals){
////        return empService.updateEmp(id,empDetals);
////    }
//
//    @GetMapping("/employees/edit/{id}")
//    public String editEmpForm(@PathVariable(value = "id") Long id, Model model, HttpSession session){
//        if(session.getAttribute("login") != null && (boolean) session.getAttribute("login")) {
//            model.addAttribute("employees", empService.getEmpById(id));
//            return "editEmp";
//        }
//        else return "redirect:/";
//    }
//
//    @PostMapping("/employees/{id}")
//    public String updateEmp(@PathVariable(value = "id") Long id,
//                            @ModelAttribute("employees") Employee emp){
//
//        //lấy dữ liệu từ database từ id
//        Employee existEmp = empService.getEmpById(id);
//        existEmp.setId(id);
//        existEmp.setFirstName(emp.getFirstName());
//        existEmp.setLastName(emp.getLastName());
//        existEmp.setEmailId(emp.getEmailId());
//        existEmp.setDob(emp.getDob());
//        existEmp.setAddress(emp.getAddress());
//
//        empService.updateEmp(existEmp);
//        return "redirect:/employees";
//    }
//
////    @PutMapping(value = "/employees/{empId}")
////    public void getEmployee(@PathVariable(value = "empId") Long id,
////                            @RequestParam(required = false) String email,
////                            @RequestParam(required = false) String fname,
////                            @RequestParam(required = false) String lname,
////                            @RequestParam(required = false) LocalDate dob){
////        empService.updateEmp(id,email,fname,lname,dob);
////    }
//
//
//
//    //Delete database
//    @GetMapping("/employees/delete/{id}")
//    public String deleteEmployee(@PathVariable(value="id") Long id, HttpSession session ){
//        if(session.getAttribute("login") != null && (boolean) session.getAttribute("login")) {
//            empService.deleteEmp(id);
//            return "redirect:/employees";
//        }
//        else return "redirect:/";
//    }
}
