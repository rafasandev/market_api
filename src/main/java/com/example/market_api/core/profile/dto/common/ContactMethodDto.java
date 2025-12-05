package com.example.market_api.core.profile.dto.common;

import com.example.market_api.core.profile.model.enums.ContactChannel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactMethodDto {
    private ContactChannel channel;
    private String value;
}
