package com.example.market_api.core.profile.dto.individual;

import java.util.List;
import java.util.UUID;

import com.example.market_api.core.profile.dto.common.ContactMethodDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndividualProfileResponseDto {
    private UUID id;
    private String name;
    private String cpf;
    private List<ContactMethodDto> contactMethods;
}
