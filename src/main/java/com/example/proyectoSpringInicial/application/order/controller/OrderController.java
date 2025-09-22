package com.example.proyectoSpringInicial.application.order.controller;

import com.example.proyectoSpringInicial.application.order.dto.OrderDTO;
import com.example.proyectoSpringInicial.application.order.mapper.OrderMapper;
import com.example.proyectoSpringInicial.application.order.model.Order;
import com.example.proyectoSpringInicial.application.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.findAllOrders().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(orderMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order newOrder = orderMapper.toEntity(orderDTO);
            Order createdOrder = orderService.createOrder(newOrder);
            return new ResponseEntity<>(orderMapper.toDTO(createdOrder), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}