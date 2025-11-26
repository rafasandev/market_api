package com.example.solid_classes.core.product_variation.ports;

import java.util.List;
import java.util.UUID;

import com.example.solid_classes.common.ports.NamedCrudPort;
import com.example.solid_classes.core.product_variation.model.ProductVariation;

public interface ProductVariationPort extends NamedCrudPort<ProductVariation>{
    
    List<ProductVariation> findByProductId(UUID productId);
}
