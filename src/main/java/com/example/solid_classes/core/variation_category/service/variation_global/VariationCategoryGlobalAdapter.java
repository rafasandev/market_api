package com.example.solid_classes.core.variation_category.service.variation_global;

import org.springframework.stereotype.Service;

import com.example.solid_classes.common.base.NamedCrudAdapter;
import com.example.solid_classes.core.variation_category.model.variation_global.VariationCategoryGlobal;
import com.example.solid_classes.core.variation_category.ports.VariationCategoryGlobalPort;
import com.example.solid_classes.core.variation_category.repository.VariationCategoryGlobalRepository;

@Service
public class VariationCategoryGlobalAdapter
        extends NamedCrudAdapter<VariationCategoryGlobal, VariationCategoryGlobalRepository>
        implements VariationCategoryGlobalPort {

    public VariationCategoryGlobalAdapter(VariationCategoryGlobalRepository variationCategoryRepository) {
        super(variationCategoryRepository, "Categoria de Variação Global");
    }
}
