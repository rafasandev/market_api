package com.example.solid_classes.core.variation_category.ports;

import java.util.Optional;

import com.example.solid_classes.common.ports.NamedCrudPort;
import com.example.solid_classes.core.variation_category.model.variation_global.VariationCategoryGlobal;

public interface VariationCategoryGlobalPort extends NamedCrudPort<VariationCategoryGlobal> {
	boolean existsByName(String name);

	Optional<VariationCategoryGlobal> findByName(String name);
}
