package com.example.solid_classes.core.profile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solid_classes.core.profile.dto.company.CompanyProfileForm;
import com.example.solid_classes.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileForm;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.solid_classes.core.profile.service.RegisterProfileUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final RegisterProfileUseCase registerProfileUseCase;
    private final com.example.solid_classes.core.profile.service.company.CompanyProfileService companyProfileService;
    private final com.example.solid_classes.core.profile.mapper.ProfileMapper profileMapper;

    // Endpoint para registro de perfil individual
    @PostMapping("/individual")
    public ResponseEntity<IndividualProfileResponseDto> registerIndividualProfile(@Valid @RequestBody IndividualProfileForm profileForm) {
        IndividualProfileResponseDto responseDto = registerProfileUseCase.registerIndividual(profileForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Endpoint para registro de perfil de empresa
    @PostMapping("/company")
    public ResponseEntity<CompanyProfileResponseDto> registerCompanyProfile(@Valid @RequestBody CompanyProfileForm profileForm) {
        CompanyProfileResponseDto responseDto = registerProfileUseCase.registerCompany(profileForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @org.springframework.web.bind.annotation.GetMapping("/company")
    public ResponseEntity<java.util.List<CompanyProfileResponseDto>> getAllCompanies() {
        java.util.List<com.example.solid_classes.core.profile.model.company.CompanyProfile> companies = 
            companyProfileService.getAllCompanies();
        java.util.List<CompanyProfileResponseDto> response = companies.stream()
            .map(profileMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/company/{id}")
    public ResponseEntity<CompanyProfileResponseDto> getCompanyById(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID id) {
        com.example.solid_classes.core.profile.model.company.CompanyProfile company = 
            companyProfileService.getById(id);
        CompanyProfileResponseDto response = profileMapper.toResponseDto(company);
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/company/cnpj/{cnpj}")
    public ResponseEntity<CompanyProfileResponseDto> getCompanyByCnpj(
            @org.springframework.web.bind.annotation.PathVariable String cnpj) {
        com.example.solid_classes.core.profile.model.company.CompanyProfile company = 
            companyProfileService.getByCnpj(cnpj);
        CompanyProfileResponseDto response = profileMapper.toResponseDto(company);
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/company/sector/{businessSector}")
    public ResponseEntity<java.util.List<CompanyProfileResponseDto>> getCompaniesByBusinessSector(
            @org.springframework.web.bind.annotation.PathVariable 
            com.example.solid_classes.core.profile.model.company.enums.BusinessSector businessSector) {
        java.util.List<com.example.solid_classes.core.profile.model.company.CompanyProfile> companies = 
            companyProfileService.getCompaniesByBusinessSector(businessSector);
        java.util.List<CompanyProfileResponseDto> response = companies.stream()
            .map(profileMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }
}
