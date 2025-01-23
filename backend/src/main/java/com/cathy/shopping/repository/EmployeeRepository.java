package com.cathy.shopping.repository;

import com.cathy.shopping.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmailId(String email);
    boolean existsByUsername(String username);
}
