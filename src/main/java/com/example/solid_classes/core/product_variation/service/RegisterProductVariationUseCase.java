package com.example.solid_classes.core.product_variation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.common.exception.BusinessRuleException;
import com.example.solid_classes.core.product.model.Product;
import com.example.solid_classes.core.product.service.ProductService;
import com.example.solid_classes.core.product_variation.dto.ProductVariationForm;
import com.example.solid_classes.core.product_variation.dto.ProductVariationResponseDto;
import com.example.solid_classes.core.product_variation.mapper.ProductVariationMapper;
import com.example.solid_classes.core.product_variation.model.ProductVariation;
import com.example.solid_classes.core.variation_category.model.VariationCategoryEntity;
import com.example.solid_classes.core.variation_category.service.variation_global.VariationCategoryGlobalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterProductVariationUseCase {

    private final ProductVariationService productVariationService;
    private final ProductService productService;
    private final VariationCategoryGlobalService variationCategoryGlobalService;
    private final ProductVariationMapper productVariationMapper;

    @Transactional
    public ProductVariationResponseDto registerProductVariation(ProductVariationForm variationForm) {
        // Buscar dependências via Services
        VariationCategoryEntity category = variationCategoryGlobalService.getById(variationForm.getVariationCategoryId());
        Product product = productService.getById(variationForm.getProductId());

        // Validação de categoria ativa
        if (!category.isActive()) {
            throw new BusinessRuleException("Categoria de variação inativa. Operação falhou");
        }

        // Criar e persistir via Service
        ProductVariation newVariation = productVariationMapper.toEntity(variationForm, category, product);
        ProductVariation savedVariation = productVariationService.save(newVariation);
        
        return productVariationMapper.toResponseDto(savedVariation);
    }
}
