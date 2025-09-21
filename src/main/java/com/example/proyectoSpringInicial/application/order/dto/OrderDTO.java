package com.example.proyectoSpringInicial.application.order.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private double totalAmount;
    private String status;
    private List<OrderItemDTO> orderItems;
}