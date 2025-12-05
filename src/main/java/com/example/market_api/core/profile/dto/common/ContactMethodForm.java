package com.example.market_api.core.profile.dto.common;

import com.example.market_api.core.profile.model.enums.ContactChannel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContactMethodForm {

    @NotNull(message = "O canal de contato é obrigatório")
    private ContactChannel channel;

    @NotBlank(message = "O valor do contato é obrigatório")
    private String value;
}
