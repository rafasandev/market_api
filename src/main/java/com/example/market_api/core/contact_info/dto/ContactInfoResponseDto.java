package com.example.market_api.core.contact_info.dto;

import com.example.market_api.core.contact_info.model.enums.ContactChannel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactInfoResponseDto {
    private ContactChannel channel;
    private String value;
}
