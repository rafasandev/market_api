package com.example.solid_classes.core.variation_category.service.variation_seller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.variation_category.model.variation_seller.VariationCategorySeller;
import com.example.solid_classes.core.variation_category.ports.VariationCategorySellerPort;

import lombok.RequiredArgsConstructor;

/**
 * Service que encapsula o Port e adiciona validações leves.
 */
@Service
@RequiredArgsConstructor
public class VariationCategorySellerService {
    
    private final VariationCategorySellerPort variationCategorySellerPort;

    // Métodos CRUD - delegam para o Port
    public VariationCategorySeller getById(UUID id) {
        return variationCategorySellerPort.getById(id);
    }

    public VariationCategorySeller save(VariationCategorySeller variationCategory) {
        return variationCategorySellerPort.save(variationCategory);
    }

    public List<VariationCategorySeller> findAll() {
        return variationCategorySellerPort.findAll();
    }
}
