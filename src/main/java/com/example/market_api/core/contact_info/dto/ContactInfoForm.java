package com.example.market_api.core.contact_info.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ContactInfoForm {

    @NotNull(message = "O tipos de canal de contato é obrigatório")
    private UUID contactTypeId;

    @NotBlank(message = "O valor do contato é obrigatório")
    private String value;

    @NotNull(message = "O usuário é obrigatório")
    private UUID userId;
}
