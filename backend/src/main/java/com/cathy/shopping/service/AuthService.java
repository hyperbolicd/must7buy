package com.cathy.shopping.service;

import com.cathy.shopping.dto.JwtResponse;
import com.cathy.shopping.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    public JwtResponse verify(User user) {
        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(auth.isAuthenticated()) {
            return new JwtResponse(jwtService.generateToken(user), new Date(System.currentTimeMillis() + jwtService.getExpiredTime()).getTime());
        } else {
            throw new BadCredentialsException("Invalid username or password for user: " + user.getUsername());
        }
    }
}
