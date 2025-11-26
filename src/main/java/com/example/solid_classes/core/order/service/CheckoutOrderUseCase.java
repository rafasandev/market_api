package com.example.solid_classes.core.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.common.exception.UserRuleException;
import com.example.solid_classes.core.cart.model.Cart;
import com.example.solid_classes.core.cart.service.CartService;
import com.example.solid_classes.core.cart_item.model.CartItem;
import com.example.solid_classes.core.order.dto.OrderCheckoutForm;
import com.example.solid_classes.core.order.dto.OrderResponseDto;
import com.example.solid_classes.core.order.mapper.OrderMapper;
import com.example.solid_classes.core.order.model.Order;
import com.example.solid_classes.core.order.model.enums.OrderStatus;
import com.example.solid_classes.core.order_item.model.OrderItem;
import com.example.solid_classes.core.order_item.service.OrderItemService;
import com.example.solid_classes.core.profile.model.company.CompanyProfile;
import com.example.solid_classes.core.profile.model.individual.IndividualProfile;
import com.example.solid_classes.core.profile.service.individual.IndividualProfileService;

import lombok.RequiredArgsConstructor;

/**
 * UseCase responsável por orquestrar o processo de checkout.
 * Usa Services e componentes auxiliares para seguir SRP.
 */
@Service
@RequiredArgsConstructor
public class CheckoutOrderUseCase {

    // Services para acesso aos dados
    private final OrderService orderService;
    private final IndividualProfileService individualProfileService;
    private final CartService cartService;
    private final OrderItemService orderItemService;

    // Componentes auxiliares (SRP)
    private final StockValidator stockValidator;
    private final PickupCodeGenerator pickupCodeGenerator;
    private final OrderCalculator orderCalculator;

    // Mappers
    private final OrderMapper orderMapper;

    @Transactional
    public List<OrderResponseDto> checkout(OrderCheckoutForm orderCheckoutForm) {
        // Buscar entidades via Services
        Cart cart = cartService.getById(orderCheckoutForm.getCartId());
        IndividualProfile customer = individualProfileService.getById(orderCheckoutForm.getCustomerId());

        // Validações de regras de negócio
        validateCartOwnership(cart, customer);
        validateCartNotEmpty(cart);
        
        // Validar estoque usando componente dedicado (SRP)
        stockValidator.validateStock(cart.getItems());

        // Agrupar itens por vendedor
        Map<CompanyProfile, List<CartItem>> itemsBySeller = groupItemsBySeller(cart.getItems());

        // Processar cada pedido por vendedor
        List<Order> savedOrders = processOrdersBySeller(cart, itemsBySeller);
        
        // Limpar carrinho após sucesso
        clearCartItems(cart);
        
        return orderMapper.toResponseDtoList(savedOrders);
    }

    /**
     * Valida se o carrinho pertence ao cliente.
     */
    private void validateCartOwnership(Cart cart, IndividualProfile customer) {
        if (!cart.getId().equals(customer.getId())) {
            throw new UserRuleException("Carrinho não pertence ao usuário atual");
        }
    }

    /**
     * Valida se o carrinho não está vazio.
     */
    private void validateCartNotEmpty(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new UserRuleException("Carrinho está vazio");
        }
    }

    /**
     * Agrupa itens do carrinho por vendedor (empresa).
     */
    private Map<CompanyProfile, List<CartItem>> groupItemsBySeller(List<CartItem> items) {
        return items.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getCompany()));
    }

    /**
     * Processa e cria pedidos agrupados por vendedor.
     */
    private List<Order> processOrdersBySeller(Cart cart, Map<CompanyProfile, List<CartItem>> itemsBySeller) {
        List<Order> savedOrders = new ArrayList<>();

        for (Map.Entry<CompanyProfile, List<CartItem>> entry : itemsBySeller.entrySet()) {
            CompanyProfile seller = entry.getKey();
            List<CartItem> storeItems = entry.getValue();

            // Calcular total usando componente dedicado (SRP)
            BigDecimal orderTotal = orderCalculator.calculateOrderTotal(storeItems);

            // Criar pedido
            Order order = Order.builder()
                    .customer(cart.getProfile())
                    .company(seller)
                    .pickUpcode(pickupCodeGenerator.generateUniqueCode()) // Componente dedicado (SRP)
                    .status(OrderStatus.PENDENTE)
                    .orderTotal(orderTotal)
                    .build();

            // Processar itens do pedido
            List<OrderItem> orderItems = processOrderItems(storeItems, order);
            order.setOrderItems(orderItems);

            // Salvar pedido via Service
            savedOrders.add(orderService.save(order));
        }

        return savedOrders;
    }

    /**
     * Processa itens do pedido: reserva, diminui estoque e cria OrderItem.
     */
    private List<OrderItem> processOrderItems(List<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> {
                    // Reservar e diminuir estoque
                    cartItem.reserve();
                    cartItem.getProduct().decreaseStock(cartItem.getProductQuantity());
                    
                    // Criar snapshot do item no pedido
                    return createOrderItemSnapshot(cartItem, order);
                })
                .toList();
    }

    /**
     * Cria snapshot do CartItem como OrderItem.
     */
    private OrderItem createOrderItemSnapshot(CartItem cartItem, Order order) {
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(cartItem.getProduct())
                .productName(cartItem.getProduct().getProductName())
                .productPrice(cartItem.getUnitPriceSnapshot())
                .productQuantity(cartItem.getProductQuantity())
                .subtotal(cartItem.calculateSubtotal())
                .build();
        
        return orderItemService.save(orderItem);
    }

    /**
     * Limpa itens do carrinho.
     */
    private void clearCartItems(Cart cart) {
        if (cart.getItems() != null) {
            cart.getItems().clear();
            cartService.save(cart);
        }
    }
}
