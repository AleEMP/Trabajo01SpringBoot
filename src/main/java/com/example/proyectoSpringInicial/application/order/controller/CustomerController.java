package com.example.proyectoSpringInicial.application.order.controller;

import com.example.proyectoSpringInicial.application.order.dto.CustomerDTO;
import com.example.proyectoSpringInicial.application.order.exception.NotFoundException;
import com.example.proyectoSpringInicial.application.order.mapper.CustomerMapper;
import com.example.proyectoSpringInicial.application.order.model.Customer;
import com.example.proyectoSpringInicial.application.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAllCustomers().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id)
                .map(customerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer newCustomer = customerMapper.toEntity(customerDTO);
            newCustomer = customerService.saveCustomer(newCustomer);
            return new ResponseEntity<>(customerMapper.toDTO(newCustomer), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO updatedCustomerDTO) {
        try {
            Customer result = customerService.updateCustomer(id, customerMapper.toEntity(updatedCustomerDTO));
            return ResponseEntity.ok(customerMapper.toDTO(result));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}