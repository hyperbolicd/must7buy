package com.cathy.shopping.service;

import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Product;
import com.cathy.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Add service methods here
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Product not exist with id: " + id));
    }
    
    public Product updateProduct(Integer id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        return productRepository.save(existingProduct);
    }

    public boolean deleteProduct(Integer id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return true;
    }
       
}
