package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        employee.setUsername(generateUsername());
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee createEmployee(Employee employee, byte[] photo) {
        employee.setPhoto(photo);
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Employee not exist with id: " + id));
    }

    public Boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existeingEmployee = getEmployeeById(id);

        if(updatedEmployee.getUsername() != null)
            existeingEmployee.setUsername(updatedEmployee.getUsername());
        if(updatedEmployee.getPassword() != null)
            existeingEmployee.setPassword(encoder.encode(updatedEmployee.getPassword()));
        if(updatedEmployee.getDisplayName() != null)
            existeingEmployee.setDisplayName(updatedEmployee.getDisplayName());
        if(updatedEmployee.getEmail() != null)
            existeingEmployee.setEmail(updatedEmployee.getEmail());
        if(updatedEmployee.getAddress() != null)
            existeingEmployee.setAddress(updatedEmployee.getAddress());
        if(updatedEmployee.getPhoto() != null)
            existeingEmployee.setPhoto(updatedEmployee.getPhoto());
        if(updatedEmployee.getHireDate() != null)
            existeingEmployee.setHireDate(updatedEmployee.getHireDate());
        if(updatedEmployee.getRole() != null) // ADMIN
            existeingEmployee.setRole(updatedEmployee.getRole());

        return employeeRepository.save(existeingEmployee);
    }

    public boolean deleteEmployee(@PathVariable long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        return true;
    }

    private String generateUsername() {
        int year = LocalDate.now().getYear();
        // 查詢當年最大流水號（例如 "E2024005" -> 5）
        String lastUsername = employeeRepository.findTopByOrderByIdDesc().get().getUsername();
        int sequence = (lastUsername != null) ? Integer.parseInt(lastUsername.substring(lastUsername.length() - 3)) + 1 : 1;
        // 格式化為 "E2024001"
        return "E" + year + String.format("%03d", sequence);
    }
}
