package com.example.solid_classes.core.order.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.solid_classes.common.exception.UserRuleException;
import com.example.solid_classes.core.cart_item.model.CartItem;
import com.example.solid_classes.core.product_variation.model.ProductVariation;
import com.example.solid_classes.core.product_variation.service.ProductVariationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockValidator {

    private final ProductVariationService productVariationService;

    public void validateStock(List<CartItem> items) {
        for (CartItem item : items) {
            ProductVariation variation = productVariationService.getById(item.getProductVariationId());
            if (!variation.hasStock(item.getItemQuantity())) {
                throw new UserRuleException(
                    String.format("Produto não possui estoque suficiente. Disponível: %d, Solicitado: %d",
                        variation.getStockQuantity(),
                        item.getItemQuantity())
                );
            }
        }
    }
}
