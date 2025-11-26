package com.example.solid_classes.core.cart_item.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.cart_item.model.CartItem;
import com.example.solid_classes.core.cart_item.ports.CartItemPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemPort cartItemPort;

    public CartItem getById(UUID id) {
        return cartItemPort.getById(id);
    }

    public Optional<CartItem> getByProductIdAndCartId(UUID productId, UUID cartId) {
        return cartItemPort.getByProductIdAndCartId(productId, cartId);
    }

    public CartItem save(CartItem cartItem) {
        return cartItemPort.save(cartItem);
    }

    public List<CartItem> findAll() {
        return cartItemPort.findAll();
    }

    public void deleteById(UUID id) {
        cartItemPort.deleteById(id);
    }
}
