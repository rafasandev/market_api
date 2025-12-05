package com.example.market_api.core.contact_info.dto;

import com.example.market_api.core.contact_info.model.enums.ContactChannel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContactInfoForm {

    @NotNull(message = "O nome do canal de contato é obrigatório")
    private ContactChannel channel;

    @NotBlank(message = "O valor do contato é obrigatório")
    private String value;
}
