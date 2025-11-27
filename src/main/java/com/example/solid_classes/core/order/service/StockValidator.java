package com.example.solid_classes.core.order.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.solid_classes.common.exception.UserRuleException;
import com.example.solid_classes.core.cart_item.model.CartItem;

/**
 * Componente responsável por validar estoque de itens do carrinho.
 * Segue SRP - responsabilidade única de validação de estoque.
 */
@Component
public class StockValidator {

    /**
     * Valida se todos os itens têm estoque suficiente.
     * 
     * @param items Lista de itens do carrinho
     * @throws UserRuleException se algum item não tiver estoque suficiente
     */
    public void validateStock(List<CartItem> items) {
        for (CartItem item : items) {
            if (!item.getProduct().productHasStock(item.getItemQuantity())) {
                throw new UserRuleException(
                    String.format("Produto '%s' não possui estoque suficiente. Disponível: %d, Solicitado: %d",
                        item.getProduct().getProductName(),
                        item.getProduct().getStockQuantity(),
                        item.getItemQuantity())
                );
            }
        }
    }
}
