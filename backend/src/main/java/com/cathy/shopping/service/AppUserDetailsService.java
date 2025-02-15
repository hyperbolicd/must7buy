package com.cathy.shopping.service;

import com.cathy.shopping.model.AppUserDetails;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.model.User;
import com.cathy.shopping.repository.CustomerRepository;
import com.cathy.shopping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<User> findByUsername(String username) {
        Optional<User> user = employeeRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = customerRepository.findByUsername(username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User [%s] not found.", username)));

        return new AppUserDetails(user);
    }
}
