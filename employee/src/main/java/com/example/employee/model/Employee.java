package com.example.employee.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email_id")
    private String email_id;

    @Transient
    @Column(name = "age")
    private Integer age;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "address")
    private String address;

    @Column(name = "isDelete")
    boolean isDelete;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName,
                    String email_id, LocalDate dob, String address, boolean isDelete) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email_id = email_id;
        this.dob = dob;
        this.address=address;
        this.isDelete=isDelete;
    }

    public Employee(String firstName, String lastName, String email_id, LocalDate dob, String address,boolean isDelete) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email_id = email_id;
        this.dob = dob;
        this.address=address;
        this.isDelete=isDelete;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getemail_id() {
        return email_id;
    }

    public void setemail_id(String email_id) {
        this.email_id = email_id;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email_id='" + email_id + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", isDelete='" + isDelete + '\'' +
                '}';
    }
}
