package com.example.employee.repository;

import com.example.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //SELECT * FROM Employee WHERE email_id=?
    @Query("SELECT e from Employee e where e.email_id= ?1 and e.isDelete=false")
    Optional<Employee> findEmployeeByEmail(String email);

    @Query("SELECT e from Employee e where e.email_id= ?1 and e.isDelete=false ")
    Employee findEmployeeByEmail1(String email);

    @Query("SELECT e from Employee e where e.isDelete=false ")
    List<Employee> findEmployeeByEmailNotIsDelete();

    @Query("SELECT e from Employee e where e.isDelete=false and concat(e.firstName, e.lastName, e.email_id, e.address, e.dob) like %?1% ")
    public List<Employee> findAll(String keyword);

    @Query("SELECT e from Employee e where e.id= ?1 and e.isDelete = false ")
    Employee findUserById(Long id);

}
