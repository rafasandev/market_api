package com.example.market_api.core.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.core.contact_info.dto.ContactInfoResponseDto;
import com.example.market_api.core.contact_info.mapper.ContactInfoMapper;
import com.example.market_api.core.contact_info.model.ContactInfo;
import com.example.market_api.core.contact_info.service.ContactInfoService;
import com.example.market_api.core.profile.dto.common.ProfileContactConfigurationForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.market_api.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.market_api.core.profile.mapper.ProfileMapper;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.model.individual.IndividualProfile;
import com.example.market_api.core.profile.service.company.CompanyProfileService;
import com.example.market_api.core.profile.service.individual.IndividualProfileService;
import com.example.market_api.core.user.model.User;
import com.example.market_api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigureProfileContactsUseCase {

    private final CompanyProfileService companyProfileService;
    private final IndividualProfileService individualProfileService;
    private final ContactInfoService contactInfoService;
    private final UserService userService;

    private final ContactInfoMapper contactInfoMapper;
    private final ProfileMapper profileMapper;

    @Transactional
    public CompanyProfileResponseDto configureCompanyContacts(ProfileContactConfigurationForm form) {
        User loggedUser = userService.getLoggedInUser();
        CompanyProfile company = companyProfileService.getById(loggedUser.getId());

        // Geração de novos contatos e substituição dos antigos
        List<ContactInfo> newContacts = contactInfoService.replaceContacts(company.getUser(), form.getContacts());
        companyProfileService.save(company);
        
        List<ContactInfoResponseDto> contactInfosDto = contactInfoMapper.mapContactInfos(newContacts);
        return profileMapper.toResponseDto(company, contactInfosDto);
    }

    @Transactional
    public IndividualProfileResponseDto configureIndividualContacts(ProfileContactConfigurationForm form) {
        User loggedUser = userService.getLoggedInUser();
        IndividualProfile client = individualProfileService.getById(loggedUser.getId());

        // Geração de novos contatos e substituição dos antigos
        List<ContactInfo> newContacts = contactInfoService.replaceContacts(client.getUser(), form.getContacts());
        individualProfileService.save(client);

        List<ContactInfoResponseDto> contactInfosDto = contactInfoMapper.mapContactInfos(newContacts);
        return profileMapper.toResponseDto(client, contactInfosDto);
    }
}
