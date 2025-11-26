package com.example.solid_classes.core.user.controller;

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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RegisterProfileUseCase registerProfileUseCase;

    @PostMapping("/register/individual")
    public ResponseEntity<IndividualProfileResponseDto> registerIndividualProfile(
            @Valid @RequestBody IndividualProfileForm profileForm) {
        IndividualProfileResponseDto newClient = registerProfileUseCase.registerIndividual(profileForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PostMapping("/register/company")
    public ResponseEntity<CompanyProfileResponseDto> registerCompanyProfile(
            @Valid @RequestBody CompanyProfileForm profileForm) {
        CompanyProfileResponseDto newSeller = registerProfileUseCase.registerCompany(profileForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeller);
    }
}
