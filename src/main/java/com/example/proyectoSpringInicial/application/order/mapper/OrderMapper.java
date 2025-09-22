package com.example.proyectoSpringInicial.application.order.mapper;

import com.example.proyectoSpringInicial.application.order.dto.OrderDTO;
import com.example.proyectoSpringInicial.application.order.dto.OrderItemDTO;
import com.example.proyectoSpringInicial.application.order.model.Customer;
import com.example.proyectoSpringInicial.application.order.model.Order;
import com.example.proyectoSpringInicial.application.order.model.OrderItem;
import com.example.proyectoSpringInicial.application.order.model.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());

        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getId());
        }

        if (order.getOrderItems() != null) {
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(this::toOrderItemDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) return null;

        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus());

        Customer customer = new Customer();
        customer.setId(dto.getCustomerId());
        order.setCustomer(customer);

        if (dto.getOrderItems() != null) {
            order.setOrderItems(dto.getOrderItems().stream()
                    .map(this::toOrderItemEntity)
                    .collect(Collectors.toList()));
        }

        return order;
    }

    public OrderItem toOrderItemEntity(OrderItemDTO dto, Order order) {
        if (dto == null) return null;

        OrderItem item = new OrderItem();
        Product product = new Product();
        product.setId(dto.getProductId());
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setOrder(order);

        return item;
    }

    private OrderItem toOrderItemEntity(OrderItemDTO dto) {
        if (dto == null) return null;

        OrderItem item = new OrderItem();
        Product product = new Product();
        product.setId(dto.getProductId());
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());

        return item;
    }

    private OrderItemDTO toOrderItemDTO(OrderItem item) {
        if (item == null) return null;

        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());

        return dto;
    }
}