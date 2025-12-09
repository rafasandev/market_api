package com.example.market_api.core.profile.service.company.use_case;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.core.contact_info.dto.ContactInfoResponseDto;
import com.example.market_api.core.contact_info.mapper.ContactInfoMapper;
import com.example.market_api.core.contact_info.model.ContactInfo;
import com.example.market_api.core.payment_method.model.PaymentMethod;
import com.example.market_api.core.payment_method.service.PaymentMethodService;
import com.example.market_api.core.profile.dto.company.CompanyPaymentMethodsForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.market_api.core.profile.mapper.ProfileMapper;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.service.company.CompanyProfileService;
import com.example.market_api.core.user.model.User;
import com.example.market_api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigureCompanyPaymentMethodsUseCase {

    private final CompanyProfileService companyProfileService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;
    private final ContactInfoMapper contactInfoMapper;
    
    private final ProfileMapper profileMapper;

    @Transactional
    public CompanyProfileResponseDto configurePaymentMethods(CompanyPaymentMethodsForm form) {
        User loggedUser = userService.getLoggedInUser();
        CompanyProfile company = companyProfileService.getById(loggedUser.getId());
        Set<PaymentMethod> paymentMethods = paymentMethodService.getAllByIds(form.getPaymentMethodIds());
        replacePaymentMethods(company, paymentMethods);
        CompanyProfile saved = companyProfileService.save(company);

        List<ContactInfo> contactInfos = loggedUser.getContacts();
        List<ContactInfoResponseDto> contactInfosDto = contactInfoMapper.mapContactInfos(contactInfos);
        return profileMapper.toResponseDto(saved, contactInfosDto);
    }

    private void replacePaymentMethods(CompanyProfile company, Set<PaymentMethod> newMethods) {
        if (company.getPaymentMethods() != null && !company.getPaymentMethods().isEmpty()) {
            List.copyOf(company.getPaymentMethods()).forEach(company::removePaymentMethod);
        }
        newMethods.forEach(company::addPaymentMethod);
    }
}
