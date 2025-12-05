package com.example.market_api.core.profile.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.market_api.core.contact_info.dto.ContactInfoResponseDto;
import com.example.market_api.core.contact_info.dto.ContactInfoForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.market_api.core.profile.dto.individual.IndividualProfileForm;
import com.example.market_api.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.model.company.enums.PaymentMethod;
import com.example.market_api.core.profile.model.individual.IndividualProfile;
import com.example.market_api.core.profile.model.value.ContactMethod;
import com.example.market_api.core.user.model.User;

@Component
public class ProfileMapper {
    public CompanyProfile toEntity(CompanyProfileForm profileForm, User user) {

        CompanyProfile companyProfile = CompanyProfile.builder()
                .user(user)
                .companyName(profileForm.getCompanyName())
                .cnpj(profileForm.getCnpj())
                .businessSector(profileForm.getBusinessSector())
            .contactMethods(mapContactFormsToEntities(profileForm.getContactMethods()))
            .acceptedPaymentMethods(mapPaymentMethods(profileForm.getAcceptedPaymentMethods()))
                .active(true)
                .build();
        return companyProfile;
    }

    public IndividualProfile toEntity(IndividualProfileForm profileForm, User user) {

        IndividualProfile individualProfile = IndividualProfile.builder()
                .user(user)
                .name(profileForm.getName())
                .cpf(profileForm.getCpf())
                .contactMethods(mapContactFormsToEntities(profileForm.getContactMethods()))
                .active(true)
                .build();
        return individualProfile;
    }

    public CompanyProfileResponseDto toResponseDto(CompanyProfile savedProfile) {
        CompanyProfileResponseDto responseDto = CompanyProfileResponseDto.builder()
                .id(savedProfile.getId())
                .companyName(savedProfile.getCompanyName())
                .cnpj(savedProfile.getCnpj())
                .businessSector(savedProfile.getBusinessSector())
                .contactMethods(mapContactEntitiesToDtos(savedProfile.getContactMethods()))
                .acceptedPaymentMethods(defaultPaymentMethods(savedProfile.getAcceptedPaymentMethods()))
                .weekDaysAvailable(savedProfile.getWeekDaysAvailable())
                .dailyAvailableTimeRanges(savedProfile.getDailyAvailableTimeRanges())
                .build();
        return responseDto;
    }

    public IndividualProfileResponseDto toResponseDto(IndividualProfile savedProfile) {
        IndividualProfileResponseDto responseDto = IndividualProfileResponseDto.builder()
                .id(savedProfile.getId())
                .name(savedProfile.getName())
                .cpf(savedProfile.getCpf())
                .contactMethods(mapContactEntitiesToDtos(savedProfile.getContactMethods()))
                .build();
        return responseDto;
    }

    // private List<ContactMethod> mapContactFormsToEntities(List<ContactMethodForm> forms) {
    //     if (forms == null || forms.isEmpty()) {
    //         return new ArrayList<>();
    //     }
    //     return forms.stream()
    //             .map(form -> ContactMethod.builder()
    //                     .channel(form.getChannel())
    //                     .value(form.getValue())
    //                     .build())
    //             .collect(Collectors.toList());
    // }

    // private List<ContactMethodDto> mapContactEntitiesToDtos(List<ContactMethod> contactMethods) {
    //     if (contactMethods == null || contactMethods.isEmpty()) {
    //         return Collections.emptyList();
    //     }
    //     return contactMethods.stream()
    //             .map(contact -> ContactMethodDto.builder()
    //                     .channel(contact.getChannel())
    //                     .value(contact.getValue())
    //                     .build())
    //             .collect(Collectors.toList());
    // }

    // private Set<PaymentMethod> mapPaymentMethods(Set<PaymentMethod> methods) {
    //     if (methods == null || methods.isEmpty()) {
    //         return new HashSet<>();
    //     }
    //     return new HashSet<>(methods);
    // }

    // private Set<PaymentMethod> defaultPaymentMethods(Set<PaymentMethod> methods) {
    //     if (methods == null || methods.isEmpty()) {
    //         return Collections.emptySet();
    //     }
    //     return Set.copyOf(methods);
    // }
}
