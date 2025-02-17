package com.cathy.shopping.config;

import com.cathy.shopping.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

@Converter(autoApply = true)
public class CartConverter implements AttributeConverter<Customer.Cart, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Customer.Cart cart) {
        try {
            return mapper.writeValueAsString(cart);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    @Override
    public Customer.Cart convertToEntityAttribute(String s) {
        try {
            return mapper.readValue(s, Customer.Cart.class);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
