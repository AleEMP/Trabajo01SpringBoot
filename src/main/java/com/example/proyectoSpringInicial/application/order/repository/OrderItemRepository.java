package com.example.proyectoSpringInicial.application.order.repository;

import com.example.proyectoSpringInicial.application.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}