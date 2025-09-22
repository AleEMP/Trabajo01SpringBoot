package com.example.proyectoSpringInicial.application.order.service;
import com.example.proyectoSpringInicial.application.order.model.Customer;
import com.example.proyectoSpringInicial.application.order.model.Order;
import com.example.proyectoSpringInicial.application.order.model.OrderItem;
import com.example.proyectoSpringInicial.application.order.model.Product;
import com.example.proyectoSpringInicial.application.order.repository.CustomerRepository;
import com.example.proyectoSpringInicial.application.order.repository.OrderItemRepository;
import com.example.proyectoSpringInicial.application.order.repository.OrderRepository;
import com.example.proyectoSpringInicial.application.order.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + order.getCustomer().getId()));

        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double totalAmount = 0.0;

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + item.getProduct().getId()));

                item.setProduct(product);
                item.setPrice(product.getPrice());
                item.setOrder(order);

                totalAmount += item.getPrice() * item.getQuantity();
            }
        }

        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
}

