package com.cathy.shopping.controller;

import com.cathy.shopping.dto.JwtResponse;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.service.AuthService;
import com.cathy.shopping.service.CustomerService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AuthService authService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/registry")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        customer.setSource("Internal");
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    // validate email is available
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        Boolean isValid = !customerService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", isValid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<Customer> getCustomerByUsername(@RequestParam String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Customer customer) {
        Customer validUser = customerService.getCustomerByUsername(customer.getUsername());
        JwtResponse jwtResponse = authService.verify(customer);
        return ResponseEntity.ok(jwtResponse);
    }

}
