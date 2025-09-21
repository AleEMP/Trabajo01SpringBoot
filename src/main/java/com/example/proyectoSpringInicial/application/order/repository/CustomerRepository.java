package com.example.proyectoSpringInicial.application.order.repository;

import com.example.proyectoSpringInicial.application.order.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}