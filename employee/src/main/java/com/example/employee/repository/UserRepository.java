package com.example.employee.repository;

import com.example.employee.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT e from Users e where e.email= ?1")
    Optional<Users> findUserByEmail(String email);

    @Query("SELECT e from Users e where e.email= ?1")
    Users findUserByEmail1(String email);

}
