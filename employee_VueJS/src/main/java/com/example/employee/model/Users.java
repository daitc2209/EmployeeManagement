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

//    @Enumerated(EnumType.STRING)
//    protected Role role;

    public Users() {
    }

    public Users(String email, String password, String name, boolean isActive) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isActive = isActive;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                " email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
