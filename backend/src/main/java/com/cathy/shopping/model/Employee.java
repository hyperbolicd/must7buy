package com.cathy.shopping.model;

import com.cathy.shopping.repository.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "employees")
public class Employee extends User {

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @PrePersist
    public void setDefaultRole() {
        setRole(Role.EMPLOYEE);
    }

    public Employee() {

    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", displayName='" + getDisplayName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", photo=" + Arrays.toString(getPhoto()) +
                ", hireDate=" + hireDate +
                ", role=" + getRole() +
                ", source='" + getSource() + '\'' +
                '}';
    }
}
