package com.cathy.shopping.service;

import com.cathy.shopping.model.AppUserDetails;
import com.cathy.shopping.model.User;
import com.cathy.shopping.repository.EmployeeRepository;
import com.cathy.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User [%s] not found.", username)));

        return new AppUserDetails(user);
    }
}
