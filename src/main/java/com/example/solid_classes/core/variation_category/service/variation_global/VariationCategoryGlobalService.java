package com.example.solid_classes.core.variation_category.service.variation_global;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.variation_category.model.variation_global.VariationCategoryGlobal;
import com.example.solid_classes.core.variation_category.ports.VariationCategoryGlobalPort;

import lombok.RequiredArgsConstructor;

/**
 * Service que encapsula o Port e adiciona validações leves.
 */
@Service
@RequiredArgsConstructor
public class VariationCategoryGlobalService {

    private final VariationCategoryGlobalPort variationCategoryGlobalPort;

    // Métodos CRUD - delegam para o Port
    public VariationCategoryGlobal getById(UUID id) {
        return variationCategoryGlobalPort.getById(id);
    }

    public VariationCategoryGlobal save(VariationCategoryGlobal variationCategory) {
        return variationCategoryGlobalPort.save(variationCategory);
    }

    public List<VariationCategoryGlobal> findAll() {
        return variationCategoryGlobalPort.findAll();
    }
}
