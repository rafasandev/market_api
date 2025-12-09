package com.example.market_api.core.cart_item.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.core.cart.model.Cart;
import com.example.market_api.core.cart.service.CartService;
import com.example.market_api.core.cart_item.dto.CartItemForm;
import com.example.market_api.core.cart_item.dto.CartItemResponseDto;
import com.example.market_api.core.cart_item.mapper.CartItemMapper;
import com.example.market_api.core.cart_item.model.CartItem;
import com.example.market_api.core.product.model.Product;
import com.example.market_api.core.product.service.ProductService;
import com.example.market_api.core.product_variation.model.ProductVariation;
import com.example.market_api.core.product_variation.service.ProductVariationService;
import com.example.market_api.core.profile.model.individual.IndividualProfile;
import com.example.market_api.core.profile.service.individual.IndividualProfileService;
import com.example.market_api.core.user.model.User;
import com.example.market_api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterCartItemUseCase {

    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final ProductVariationService productVariationService;
    private final IndividualProfileService individualProfileService;
    private final UserService userService;

    private final CartItemMapper cartItemMapper;

    @Transactional
    public CartItemResponseDto registerCartItem(CartItemForm cartItemForm) {
        User loggedUser = userService.getLoggedInUser();
        IndividualProfile client = individualProfileService.getById(loggedUser.getId());

        individualProfileService.validateIsActive(client);

        Cart cart = cartService.getCartByProfileId(client.getId());
        ProductVariation variation = productVariationService.getById(cartItemForm.getProductVariationId());
        Product product = productService.getById(variation.getProductId());

        productService.validateAvailability(product);
        productVariationService.validateAvailability(variation);

        Optional<CartItem> optionalCartItem = cartItemService.getByProductVariationIdAndCartId(
                variation.getId(),
                cart.getId());
        CartItem cartItemNewOrExistent;

        if (optionalCartItem.isPresent()) {
            cartItemNewOrExistent = optionalCartItem.get();
            int newQuantity = cartItemNewOrExistent.getItemQuantity() + cartItemForm.getItemQuantity();
            validateProductAndVariationStock(product, variation, newQuantity);
            cartItemNewOrExistent.setQuantity(newQuantity);
        } else {
            validateProductAndVariationStock(product, variation, cartItemForm.getItemQuantity());
            cartItemNewOrExistent = cartItemMapper.toEntity(cartItemForm, product, cart);
        }
        CartItem savedItem = cartItemService.save(cartItemNewOrExistent);
        cart.addCartItem(cartItemNewOrExistent);
        return cartItemMapper.toResponseDto(
                savedItem,
                product.getBasePrice().add(variation.getVariationAdditionalPrice()));
    }

    private void validateProductAndVariationStock(Product product, ProductVariation variation, int requestedQuantity) {
        productService.validateStock(product, requestedQuantity);
        productVariationService.validateStock(variation, requestedQuantity);
    }
}
