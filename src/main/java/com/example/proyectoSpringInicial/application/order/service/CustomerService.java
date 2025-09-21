package com.example.proyectoSpringInicial.application.order.service;

import com.example.proyectoSpringInicial.application.order.exception.NotFoundException;
import com.example.proyectoSpringInicial.application.order.model.Customer;
import com.example.proyectoSpringInicial.application.order.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        log.info("Fetching all customers");
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id) {
        log.info("Fetching customer with id: {}", id);
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        validateCustomer(customer);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created with id: {}", savedCustomer.getId());
        return savedCustomer;
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id)
                .map(customer -> {
                    if (updatedCustomer.getFirstName() != null) {
                        customer.setFirstName(updatedCustomer.getFirstName());
                    }
                    if (updatedCustomer.getLastName() != null) {
                        customer.setLastName(updatedCustomer.getLastName());
                    }
                    if (updatedCustomer.getEmail() != null) {
                        customer.setEmail(updatedCustomer.getEmail());
                    }
                    customer.setUpdatedAt(LocalDateTime.now());
                    validateCustomer(customer);
                    Customer result = customerRepository.save(customer);
                    log.info("Customer updated with id: {}", result.getId());
                    return result;
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            log.error("Customer with id {} not found for deletion", id);
            throw new NotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
        log.info("Customer with id {} deleted successfully", id);
    }

    private void validateCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            log.error("Validation failed: First name cannot be empty");
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            log.error("Validation failed: Last name cannot be empty");
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            log.error("Validation failed: Email cannot be empty");
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!customer.getEmail().contains("@")) {
            log.error("Validation failed: Email format is invalid");
            throw new IllegalArgumentException("Email format is invalid");
        }
    }
}