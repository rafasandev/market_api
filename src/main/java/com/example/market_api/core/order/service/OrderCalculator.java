package com.example.market_api.core.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.market_api.core.cart_item.model.CartItem;

@Component
public class OrderCalculator {

    public BigDecimal calculateOrderTotal(List<CartItem> items) {
        return items.stream()
            .map(CartItem::calculateSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
