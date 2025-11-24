package com.example.solid_classes.core.variation_category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solid_classes.core.variation_category.dto.VariationCategoryResponseDto;
import com.example.solid_classes.core.variation_category.dto.variation_global.VariationCategoryGlobalForm;
import com.example.solid_classes.core.variation_category.dto.variation_seller.VariationCategorySellerForm;
import com.example.solid_classes.core.variation_category.service.RegisterVariationCategoryUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/variation-category")
@RequiredArgsConstructor
public class VariationCategoryController {

    private final RegisterVariationCategoryUseCase variationCategoryService;

    @PostMapping("/global")
    public ResponseEntity<VariationCategoryResponseDto> createGlobalVariationCategory(@RequestBody VariationCategoryGlobalForm variationForm) {
        VariationCategoryResponseDto variationCategory = variationCategoryService.registerVariationCategory(variationForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(variationCategory);
    }

    @PostMapping("/seller")
    public ResponseEntity<VariationCategoryResponseDto> createSellerVariationCategory(@RequestBody VariationCategorySellerForm variationForm) {
        VariationCategoryResponseDto variationCategory = variationCategoryService.registerVariationCategory(variationForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(variationCategory);
    }
    

}
