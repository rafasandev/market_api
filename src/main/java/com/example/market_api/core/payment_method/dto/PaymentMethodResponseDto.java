package com.example.market_api.core.payment_method.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMethodResponseDto {
    private UUID id;
    private String name;
    private String iconUrl;
}
