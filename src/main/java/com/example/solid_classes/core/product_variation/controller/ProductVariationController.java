package com.example.solid_classes.core.product_variation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solid_classes.core.product_variation.dto.ProductVariationForm;
import com.example.solid_classes.core.product_variation.dto.ProductVariationResponseDto;
import com.example.solid_classes.core.product_variation.service.RegisterProductVariationUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/product-variation")
@RequiredArgsConstructor
public class ProductVariationController {

    private final RegisterProductVariationUseCase registerProductVariationUseCase;
    private final com.example.solid_classes.core.product_variation.service.ProductVariationService productVariationService;
    private final com.example.solid_classes.core.product_variation.mapper.ProductVariationMapper productVariationMapper;

    @PostMapping
    public ResponseEntity<ProductVariationResponseDto> createProductVariation(@Valid @RequestBody ProductVariationForm variationForm) {
        ProductVariationResponseDto newVariation = registerProductVariationUseCase.registerProductVariation(variationForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVariation);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public ResponseEntity<java.util.List<ProductVariationResponseDto>> getAllVariations() {
        java.util.List<com.example.solid_classes.core.product_variation.model.ProductVariation> variations = 
            productVariationService.getAllVariations();
        java.util.List<ProductVariationResponseDto> response = variations.stream()
            .map(productVariationMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public ResponseEntity<ProductVariationResponseDto> getVariationById(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID id) {
        com.example.solid_classes.core.product_variation.model.ProductVariation variation = 
            productVariationService.getById(id);
        ProductVariationResponseDto response = productVariationMapper.toResponseDto(variation);
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/product/{productId}")
    public ResponseEntity<java.util.List<ProductVariationResponseDto>> getVariationsByProduct(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID productId) {
        java.util.List<com.example.solid_classes.core.product_variation.model.ProductVariation> variations = 
            productVariationService.getVariationsByProductId(productId);
        java.util.List<ProductVariationResponseDto> response = variations.stream()
            .map(productVariationMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }
}
