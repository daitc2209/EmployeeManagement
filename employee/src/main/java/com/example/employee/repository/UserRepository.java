package com.example.employee.repository;

import com.example.employee.model.Employee;
import com.example.employee.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT e from Users e where e.email= ?1")
    Optional<Users> findUserByEmail(String email);

    @Query("SELECT e from Users e where e.isDelete=false ")
    List<Users> findAll();

    @Query("SELECT e from Users e where e.isDelete=false and concat(e.name, e.email, e.role) like %?1% ")
    List<Users> findAll(String keyword);
}
