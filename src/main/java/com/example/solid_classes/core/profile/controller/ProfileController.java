package com.example.solid_classes.core.profile.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solid_classes.core.profile.dto.company.CompanyProfileForm;
import com.example.solid_classes.core.profile.dto.company.CompanyProfileResponseDto;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileForm;
import com.example.solid_classes.core.profile.dto.individual.IndividualProfileResponseDto;
import com.example.solid_classes.core.profile.model.company.enums.BusinessSector;
import com.example.solid_classes.core.profile.service.RegisterProfileUseCase;
import com.example.solid_classes.core.profile.service.company.GetCompanyProfileUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsável apenas por rotear requisições HTTP.
 * Não contém lógica de negócio ou mapeamento.
 */
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final RegisterProfileUseCase registerProfileUseCase;
    private final GetCompanyProfileUseCase getCompanyProfileUseCase;

    @PostMapping("/individual")
    public ResponseEntity<IndividualProfileResponseDto> registerIndividualProfile(@Valid @RequestBody IndividualProfileForm profileForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            registerProfileUseCase.registerIndividual(profileForm)
        );
    }

    @PostMapping("/company")
    public ResponseEntity<CompanyProfileResponseDto> registerCompanyProfile(@Valid @RequestBody CompanyProfileForm profileForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            registerProfileUseCase.registerCompany(profileForm)
        );
    }

    @GetMapping("/company")
    public ResponseEntity<List<CompanyProfileResponseDto>> getAllCompanies() {
        return ResponseEntity.ok(getCompanyProfileUseCase.getAllCompanies());
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyProfileResponseDto> getCompanyById(@PathVariable UUID id) {
        return ResponseEntity.ok(getCompanyProfileUseCase.getCompanyById(id));
    }

    @GetMapping("/company/cnpj/{cnpj}")
    public ResponseEntity<CompanyProfileResponseDto> getCompanyByCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(getCompanyProfileUseCase.getCompanyByCnpj(cnpj));
    }

    @GetMapping("/company/sector/{businessSector}")
    public ResponseEntity<List<CompanyProfileResponseDto>> getCompaniesByBusinessSector(@PathVariable BusinessSector businessSector) {
        return ResponseEntity.ok(getCompanyProfileUseCase.getCompaniesByBusinessSector(businessSector));
    }
}
