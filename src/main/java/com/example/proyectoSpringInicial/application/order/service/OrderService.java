package com.example.proyectoSpringInicial.application.order.service;
import com.example.proyectoSpringInicial.application.order.model.Order;
import com.example.proyectoSpringInicial.application.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    // ... otros repositorios necesarios

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        // Lógica para calcular el total de la orden
        double total = order.getOrderItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        order.setTotalAmount(total);
        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully for customer {} with id: {}", order.getCustomer().getId(), savedOrder.getId());
        return savedOrder;
    }
}