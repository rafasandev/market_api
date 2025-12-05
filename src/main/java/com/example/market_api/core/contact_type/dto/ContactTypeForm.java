package com.example.market_api.core.contact_type.dto;

import com.example.market_api.core.contact_type.model.enums.ContactChannel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContactTypeForm {
    
    @NotNull(message = "O canal de contato é obrigatório")
    private ContactChannel channel;

    @NotNull(message = "A URL base é obrigatória")
    private String baseUrl;

    private String validationRegex;

    private String iconUrl;
}
