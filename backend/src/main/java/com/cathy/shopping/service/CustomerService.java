package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder encoder;

    // Add service methods here
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        customer.setDisplayName(customer.getUsername());
        customer.setEmail(customer.getUsername());
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Customer not exist with id: " + id));
    }

    public Customer getCustomerByUsername(String username) {
        return (Customer) customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFountException("Customer not exist with username: " + username));
    }
    
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(id);
        return customerRepository.save(existingCustomer);
    }

    public boolean deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
        return true;
    }

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer.Cart getCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth===" + auth.getName());
        Customer existingCustomer = getCustomerByUsername(auth.getName());
        return existingCustomer.getCart();
    }

    public Customer updateCart(Customer.Cart cart) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth===" + auth.getName());
        Customer existingCustomer = getCustomerByUsername(auth.getName());
        existingCustomer.setCart(cart);
        System.out.println(Thread.currentThread().getStackTrace()[1] + "===" + existingCustomer);
        return customerRepository.save(existingCustomer);
    }

    public boolean isValidCart(Customer.Cart cart) {
        AtomicInteger totalPrice = new AtomicInteger(cart.getTotalPrice());
        cart.getItems().forEach( cartItem -> {
            totalPrice.addAndGet(-(int) (cartItem.getQuantity() * cartItem.getPrice().doubleValue()));
        });
        return totalPrice.get() == 0;
    }
}