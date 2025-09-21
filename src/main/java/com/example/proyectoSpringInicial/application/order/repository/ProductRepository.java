package com.example.proyectoSpringInicial.application.order.repository;

import com.example.proyectoSpringInicial.application.order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}