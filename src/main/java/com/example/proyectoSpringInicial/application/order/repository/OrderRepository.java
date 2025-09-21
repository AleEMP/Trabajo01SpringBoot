package com.example.proyectoSpringInicial.application.order.repository;


import com.example.proyectoSpringInicial.application.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}