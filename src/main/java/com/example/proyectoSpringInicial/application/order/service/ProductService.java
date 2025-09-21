package com.example.proyectoSpringInicial.application.order.service;

import com.example.proyectoSpringInicial.application.order.exception.NotFoundException;
import com.example.proyectoSpringInicial.application.order.model.Product;
import com.example.proyectoSpringInicial.application.order.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        validateProduct(product);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        log.info("Product created with id: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    if (updatedProduct.getName() != null) {
                        product.setName(updatedProduct.getName());
                    }
                    if (updatedProduct.getDescription() != null) {
                        product.setDescription(updatedProduct.getDescription());
                    }
                    if (updatedProduct.getPrice() > 0) {
                        product.setPrice(updatedProduct.getPrice());
                    }
                    if (updatedProduct.getStockQuantity() >= 0) {
                        product.setStockQuantity(updatedProduct.getStockQuantity());
                    }
                    if (updatedProduct.isActive() != product.isActive()) {
                        product.setActive(updatedProduct.isActive());
                    }

                    product.setUpdatedAt(LocalDateTime.now());
                    validateProduct(product);
                    Product result = productRepository.save(product);
                    log.info("Product updated with id: {}", result.getId());
                    return result;
                })
                .orElseThrow(() -> {
                    log.error("Product with id {} not found for update", id);
                    return new NotFoundException("Product not found with id " + id);
                });
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            log.error("Product with id {} not found for deletion", id);
            throw new NotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
        log.info("Product with id {} deleted successfully", id);
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            log.error("Validation failed: Product name cannot be empty");
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() <= 0) {
            log.error("Validation failed: Product price must be greater than zero");
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (product.getStockQuantity() < 0) {
            log.error("Validation failed: Product stock quantity cannot be negative");
            throw new IllegalArgumentException("Product stock quantity cannot be negative");
        }
    }
}