package com.example.solid_classes.core.profile.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.core.cart.service.RegisterCartUseCase;
import com.example.solid_classes.core.profile.dto.company.CompanyProfileForm;
import com.example.solid_classes.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileForm;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.solid_classes.core.profile.mapper.ProfileMapper;
import com.example.solid_classes.core.profile.model.company.CompanyProfile;
import com.example.solid_classes.core.profile.model.individual.IndividualProfile;
import com.example.solid_classes.core.profile.service.company.CompanyProfileService;
import com.example.solid_classes.core.profile.service.individual.IndividualProfileService;
import com.example.solid_classes.core.role.model.Role;
import com.example.solid_classes.core.role.model.enums.RoleName;
import com.example.solid_classes.core.role.service.RoleService;
import com.example.solid_classes.core.user.model.User;
import com.example.solid_classes.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterProfileUseCase {

    private final IndividualProfileService individualProfileService;
    private final CompanyProfileService companyProfileService;
    private final RoleService roleService;
    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final RegisterCartUseCase registerCartUseCase;

    @Transactional
    public IndividualProfileResponseDto registerIndividual(IndividualProfileForm individualForm) {
        // Buscar role via Service
        Role individualRole = roleService.getByRoleName(RoleName.INDIVIDUAL);
        
        // Criar usuário via Service
        User user = userService.signUp(individualForm.getEmail(), individualForm.getPassword(), Set.of(individualRole));

        // Criar e persistir perfil via Service
        IndividualProfile newProfile = profileMapper.toEntity(individualForm, user);
        IndividualProfile savedProfile = individualProfileService.save(newProfile);
        
        // Criar carrinho via UseCase
        registerCartUseCase.createCartOnProfileCreation(savedProfile);
        
        return profileMapper.toResponseDto(savedProfile);
    }

    @Transactional
    public CompanyProfileResponseDto registerCompany(CompanyProfileForm companyForm) {
        // Buscar role via Service
        Role companyRole = roleService.getByRoleName(RoleName.COMPANY);
        
        // Criar usuário via Service
        User user = userService.signUp(companyForm.getEmail(), companyForm.getPassword(), Set.of(companyRole));

        // Criar e persistir perfil via Service
        CompanyProfile newProfile = profileMapper.toEntity(companyForm, user);
        CompanyProfile savedProfile = companyProfileService.save(newProfile);
        
        return profileMapper.toResponseDto(savedProfile);
    }
}
