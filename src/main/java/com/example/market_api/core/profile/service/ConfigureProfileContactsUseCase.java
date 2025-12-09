package com.example.market_api.core.profile.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.common.exception.BusinessRuleException;
import com.example.market_api.core.profile.dto.common.ProfileContactConfigurationForm;
import com.example.market_api.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.market_api.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.market_api.core.profile.mapper.ProfileMapper;
import com.example.market_api.core.profile.model.ProfileEntity;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.model.individual.IndividualProfile;
import com.example.market_api.core.profile.service.company.CompanyProfileService;
import com.example.market_api.core.profile.service.individual.IndividualProfileService;
import com.example.market_api.core.contact_info.service.ContactInfoService;
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
    private final ProfileMapper profileMapper;

    @Transactional
    public CompanyProfileResponseDto configureCompanyContacts(UUID companyId, ProfileContactConfigurationForm form) {
        CompanyProfile company = companyProfileService.getById(companyId);
        User loggedUser = userService.getLoggedInUser();
        validateProfileOwnership(company, loggedUser);

        contactInfoService.replaceContacts(company.getUser(), form.getContacts());

        CompanyProfile refreshedCompany = companyProfileService.getById(companyId);
        return profileMapper.toResponseDto(refreshedCompany);
    }

    @Transactional
    public IndividualProfileResponseDto configureIndividualContacts(UUID individualId, ProfileContactConfigurationForm form) {
        IndividualProfile client = individualProfileService.getById(individualId);
        User loggedUser = userService.getLoggedInUser();
        validateProfileOwnership(client, loggedUser);

        contactInfoService.replaceContacts(client.getUser(), form.getContacts());

        IndividualProfile refreshedIndividual = individualProfileService.getById(individualId);
        if (loggedUser.userHasContactInfoFilled())
            loggedUser.setStatus(true);
        return profileMapper.toResponseDto(refreshedIndividual);
    } 

    private void validateProfileOwnership(ProfileEntity profile, User user) {
        if (profile == null || user == null || !profile.getId().equals(user.getId())) {
            throw new BusinessRuleException("Usuário logado não pode alterar os contatos desta empresa");
        }
    }
}
