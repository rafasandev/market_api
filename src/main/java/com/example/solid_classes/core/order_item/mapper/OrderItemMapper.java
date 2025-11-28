package com.example.solid_classes.core.order_item.mapper;

import org.springframework.stereotype.Component;

import com.example.solid_classes.core.cart_item.model.CartItem;
import com.example.solid_classes.core.order.model.Order;
import com.example.solid_classes.core.order_item.model.OrderItem;
import com.example.solid_classes.core.product_variation.model.ProductVariation;

@Component
public class OrderItemMapper {

    public OrderItem toOrderItemSnapshot(CartItem cartItem, Order order, ProductVariation variation) {
        return OrderItem.builder()
                .productId(cartItem.getProductId())
                .productVariationId(cartItem.getProductVariationId())
                .productName(cartItem.getProductName())
                .productVariationValue(variation.getVariationValue())
                .productPrice(cartItem.getUnitPriceSnapshot())
                .variationAdditionalPriceSnapshot(variation.getVariationAdditionalPrice())
                .orderQuantity(cartItem.getItemQuantity())
                .subtotal(cartItem.calculateSubtotal())
                .order(order)
                .build();
    }
}
