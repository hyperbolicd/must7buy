package com.cathy.shopping.controller;

import com.cathy.shopping.dto.JwtResponse;
import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.model.User;
import com.cathy.shopping.repository.EmployeeRepository;
import com.cathy.shopping.service.AuthService;
import com.cathy.shopping.service.EmployeeService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@PreAuthorize("hasAuthority('EMPLOYEE')")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthService authService;

    // get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // create employee rest api
//    @PostMapping("/employees")
//    public ResponseEntity<Employee> createEmployee(@RequestPart("employee") Employee employee, @RequestPart("photo")MultipartFile photoFile) {
//        try {
//            byte[] photoBytes = null;
//            if(photoFile != null)
//                photoBytes = photoFile.getBytes();
//            Employee createdEmployee = employeeService.createEmployee(employee, photoBytes);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    // create employee rest api
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setSource("Internal");
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // get employee by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // validate email is available
//    @PreAuthorize("permitAll()") // -> Class 等級有 @PreAuthorize("hasAuthority('USER')") 時可用，但 SecurityFilterChain 條件 .anyRequest().authenticated() 時無法到達而無效
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        Boolean isValid = !employeeService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", isValid);
        return ResponseEntity.ok(response);
    }

    // update employee rest api
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // @PermitAll -> 需用 @EnableMethodSecurity(jsr250Enabled = true) 實測不行未解
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Employee employee) {
        Employee validUser = employeeService.getEmployeeByUsername(employee.getUsername());
        JwtResponse jwtResponse = authService.verify(employee);
        return ResponseEntity.ok(jwtResponse);
    }
}
