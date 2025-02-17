package com.cathy.shopping.service;

import com.cathy.shopping.dto.linepay.PaymentResponse;
import com.cathy.shopping.exception.DataErrorException;
import com.cathy.shopping.exception.ResourceNotFountException;
import com.cathy.shopping.model.Customer;
import com.cathy.shopping.model.Order;
import com.cathy.shopping.model.OrderProduct;
import com.cathy.shopping.model.Product;
import com.cathy.shopping.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LinePayService linePayService;

    // Add service methods here
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getMyOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth===" + auth.getName());

        Customer existingCustomer = customerService.getCustomerByUsername(auth.getName());

        return orderRepository.findAllByCustomerId(existingCustomer.getId());
    }

    @Transactional
    public Order createOrder(Customer.Cart cart) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth===" + auth.getName());

        Customer existingCustomer = customerService.getCustomerByUsername(auth.getName());

        if(!customerService.isValidCart(cart)) {
            throw new DataErrorException("購物車商品金額加總與總金額不符");
        }

        Order order = new Order();
        order.setCustomer(existingCustomer);
        order.setTotalPrice(BigDecimal.valueOf(cart.getTotalPrice()));

        Order createdOrder = orderRepository.save(order);

        cart.getItems().forEach(cartItem -> {
            // 庫存減少
            Product existProduct = productService.getProductById(cartItem.getId());
            existProduct.setStock(existProduct.getStock() - cartItem.getQuantity());
            productService.updateProduct(cartItem.getId(), existProduct);
            // 建立訂單商品
            OrderProduct op = new OrderProduct();
            op.setOrder(createdOrder);
            Product product = productService.getProductById(cartItem.getId());
            op.setProduct(product);
            op.setQuantity(cartItem.getQuantity());
            op.setUnit_price(cartItem.getPrice());
            orderProductService.createOrderProduct(op);
        });

        existingCustomer.setCart(null);
        customerService.updateCustomer(existingCustomer.getId(), existingCustomer);

        return createdOrder;
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException("Order not exist with id: " + id));
    }
    
    public Order updateOrder(Integer id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
        return orderRepository.save(existingOrder);
    }

    public boolean deleteOrder(Integer id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
        return true;
    }

}
