package com.example.solid_classes.core.order_item.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.order_item.model.OrderItem;
import com.example.solid_classes.core.order_item.ports.OrderItemPort;

import lombok.RequiredArgsConstructor;

/**
 * Service que encapsula o Port e adiciona validações leves.
 */
@Service
@RequiredArgsConstructor
public class OrderItemService {
    
    private final OrderItemPort orderItemPort;

    // Métodos CRUD - delegam para o Port
    public OrderItem getById(UUID id) {
        return orderItemPort.getById(id);
    }

    public OrderItem save(OrderItem orderItem) {
        return orderItemPort.save(orderItem);
    }

    public List<OrderItem> findAll() {
        return orderItemPort.findAll();
    }
}
