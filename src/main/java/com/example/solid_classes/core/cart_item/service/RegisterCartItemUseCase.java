package com.example.solid_classes.core.cart_item.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.common.exception.UserRuleException;
import com.example.solid_classes.core.cart.model.Cart;
import com.example.solid_classes.core.cart.service.CartService;
import com.example.solid_classes.core.cart_item.dto.CartItemForm;
import com.example.solid_classes.core.cart_item.dto.CartItemResponseDto;
import com.example.solid_classes.core.cart_item.mapper.CartItemMapper;
import com.example.solid_classes.core.cart_item.model.CartItem;
import com.example.solid_classes.core.product.model.Product;
import com.example.solid_classes.core.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterCartItemUseCase {

    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @Transactional
    public CartItemResponseDto registerCartItem(CartItemForm cartItemForm) {
        // Buscar dependências via Services
        Cart cart = cartService.getCartByProfileId(cartItemForm.getUserId());
        Product product = productService.getById(cartItemForm.getProductId());

        // Validar disponibilidade
        productService.validateAvailability(product);

        // Verificar se item já existe no carrinho
        Optional<CartItem> optCart = cartItemService.getByProductIdAndCartId(
            cartItemForm.getProductId(), 
            cart.getId()
        );
        
        CartItem newItem;

        if (optCart.isPresent()) {
            // Item já existe - atualizar quantidade
            newItem = optCart.get();
            int newQuantity = newItem.getProductQuantity() + cartItemForm.getItemQuantity();

            // Validar estoque
            productService.validateStock(product, newQuantity);
            newItem.addQuantity(cartItemForm.getItemQuantity());
        } else {
            // Novo item - validar estoque e criar
            productService.validateStock(product, cartItemForm.getItemQuantity());
            
            newItem = cartItemMapper.toEntity(cartItemForm, product, cart);
            newItem.setQuantity(cartItemForm.getItemQuantity());
        }

        // Persistir via Service
        CartItem savedItem = cartItemService.save(newItem);
        
        return cartItemMapper.toResponseDto(savedItem);
    }
}
