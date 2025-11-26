package com.example.solid_classes.core.product_variation.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.core.product_variation.dto.ProductVariationResponseDto;
import com.example.solid_classes.core.product_variation.mapper.ProductVariationMapper;
import com.example.solid_classes.core.product_variation.model.ProductVariation;

import lombok.RequiredArgsConstructor;

/**
 * UseCase para operações de consulta de variações de produtos.
 * Responsável por buscar variações via Service e convertê-las em DTOs.
 */
@Service
@RequiredArgsConstructor
public class GetProductVariationUseCase {

    private final ProductVariationService productVariationService;
    private final ProductVariationMapper productVariationMapper;

    @Transactional(readOnly = true)
    public List<ProductVariationResponseDto> getAllVariations() {
        List<ProductVariation> variations = productVariationService.findAll();
        return variations.stream()
            .map(productVariationMapper::toResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductVariationResponseDto getVariationById(UUID id) {
        ProductVariation variation = productVariationService.getById(id);
        return productVariationMapper.toResponseDto(variation);
    }

    @Transactional(readOnly = true)
    public List<ProductVariationResponseDto> getVariationsByProductId(UUID productId) {
        List<ProductVariation> variations = productVariationService.findByProductId(productId);
        return variations.stream()
            .map(productVariationMapper::toResponseDto)
            .toList();
    }
}
