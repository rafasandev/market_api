package com.example.solid_classes.core.order.dto;

import com.example.solid_classes.core.order.model.enums.OrderStatus;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class OrderResponseDto {
    private OrderStatus orderStatus;
    private String pickupCode;
}
