package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee, byte[] photo) {
        employee.setPhoto(photo);
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Employee not exist with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existeingEmployee = getEmployeeById(id);

        if(updatedEmployee.getFirstName() != null)
            existeingEmployee.setFirstName(updatedEmployee.getFirstName());
        if(updatedEmployee.getLastName() != null)
            existeingEmployee.setLastName(updatedEmployee.getLastName());
        if(updatedEmployee.getEmailId() != null)
            existeingEmployee.setEmailId(updatedEmployee.getEmailId());
        if(updatedEmployee.getPhoto() != null)
            existeingEmployee.setPhoto(updatedEmployee.getPhoto());
        if(updatedEmployee.getHireDate() != null)
            existeingEmployee.setHireDate(updatedEmployee.getHireDate());

        return employeeRepository.save(existeingEmployee);
    }

    public boolean deleteEmployee(@PathVariable long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        return true;
    }
}
