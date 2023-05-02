package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;
    @Column(name = "password", nullable = false, length = 45)
//    @JsonIgnore
    private String password;
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name= "isActive", nullable = false)
    private boolean isActive;

    @Column(name= "isDelete", nullable = false)
    private boolean isDelete;

    @Column(name= "role", nullable = false)
    private String role;

    public Users() {
    }

    public Users(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isActive = true;
        this.isDelete = false;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                " email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isActive='" + isActive + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
