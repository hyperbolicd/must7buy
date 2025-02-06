package com.cathy.shopping.service;

import com.cathy.shopping.model.User;
import com.cathy.shopping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<User> findByUsername(String username) {
        User user = employeeRepository.findByUsername(username).get();
//        if (user.isEmpty()) {
//            user = customerRepository.findByUsername(username);
//        }
        return Optional.of(user);
    }

}
