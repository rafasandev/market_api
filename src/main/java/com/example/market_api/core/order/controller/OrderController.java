package com.example.market_api.core.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.market_api.core.order.dto.OrderPickupCodeForm;
import com.example.market_api.core.order.dto.OrderResponseDto;
import com.example.market_api.core.order.service.use_case.CheckoutOrderUseCase;
import com.example.market_api.core.order.service.use_case.SetOrderCompletedUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutOrderUseCase checkoutService;
    private final SetOrderCompletedUseCase setOrderCompletedUseCase;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('INDIVIDUAL')")
    public ResponseEntity<List<OrderResponseDto>> orderChechout() {
        List<OrderResponseDto> orders = checkoutService.checkout();
        return ResponseEntity.status(HttpStatus.CREATED).body(orders);
    }

    @PutMapping("/status/completed")
    public ResponseEntity<OrderResponseDto> updateOrderStatusWithCode(@RequestBody OrderPickupCodeForm pickupCodeForm)
    {   
        OrderResponseDto updatedOrder = setOrderCompletedUseCase.updateOrderStatusWithCode(pickupCodeForm);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

}
