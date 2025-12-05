package com.example.market_api.core.contact_info.dto;

import com.example.market_api.core.contact_type.model.enums.ContactChannel;

import lombok.Builder;

@Builder
public class ContactInfoResponseDto {
    private ContactChannel channel;
    private String baseUrl;
    private String iconUrl;
    private String value;
}
