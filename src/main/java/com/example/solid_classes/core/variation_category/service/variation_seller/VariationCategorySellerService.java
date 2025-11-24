package com.example.solid_classes.core.variation_category.service.variation_seller;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.variation_category.model.variation_seller.VariationCategorySeller;
import com.example.solid_classes.core.variation_category.ports.VariationCategorySellerPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariationCategorySellerService {
    
    public final VariationCategorySellerPort variationCategoryPort;

    public VariationCategorySeller getById(java.util.UUID id) {
        return variationCategoryPort.getById(id);
    }

    public VariationCategorySeller createVariationCategory(VariationCategorySeller variationCategory) {
        return variationCategoryPort.save(variationCategory);
    }
}
