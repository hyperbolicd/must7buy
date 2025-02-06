package com.cathy.shopping.repository;

import com.cathy.shopping.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    Employee findTopByOrderByIdDesc();
}
