package com.example.market_api.core.profile.dto.company;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.market_api.common.types.TimeRange;
import com.example.market_api.core.profile.dto.common.ContactMethodDto;
import com.example.market_api.core.profile.model.company.enums.BusinessSector;
import com.example.market_api.core.profile.model.company.enums.PaymentMethod;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyProfileResponseDto {
    private UUID id;
    private String companyName;
    private String cnpj;
    private BusinessSector businessSector;
    private List<ContactMethodDto> contactMethods;
    private Set<PaymentMethod> acceptedPaymentMethods;
    private List<Integer> weekDaysAvailable;
    private Map<Integer, List<TimeRange>> dailyAvailableTimeRanges;
}
