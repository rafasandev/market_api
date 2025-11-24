package com.example.solid_classes.core.variation_category.service.variation_global;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.variation_category.model.variation_global.VariationCategoryGlobal;
import com.example.solid_classes.core.variation_category.ports.VariationCategoryGlobalPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariationCategoryGlobalService {

    private final VariationCategoryGlobalPort variationCategoryPort;

    public VariationCategoryGlobal getById(java.util.UUID id) {
        return variationCategoryPort.getById(id);
    }

    public VariationCategoryGlobal createVariationCategory(VariationCategoryGlobal variationCategory) {
        return variationCategoryPort.save(variationCategory);
    }
    
}
