package com.example.market_api.core.order.dto;

import java.util.UUID;

import com.example.market_api.core.order.model.enums.OrderStatus;

import lombok.Getter;

@Getter
public class OrderPickupCodeForm {
    private String pickupCode;
    private UUID orderId;
    private UUID clientId;
    private OrderStatus newStatus;
}
