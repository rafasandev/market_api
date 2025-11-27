package com.example.solid_classes.core.product_variation.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.solid_classes.common.base.AuditableMongoEntity;
import com.example.solid_classes.core.product_variation.model.enums.VariationCategoryType;
import com.example.solid_classes.core.product_variation.model.enums.VariationValueType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductVariation extends AuditableMongoEntity {

    private UUID variationCategoryId;
    private VariationCategoryType variationCategoryType;
    private VariationValueType valueType;
    private String variationValue;
    private BigDecimal variationAdditionalPrice;

    @Setter
    private int stockQuantity;

    public boolean isAvailable() {
        return this.stockQuantity > 0;
    }

    public boolean hasStock(int requestedQuantity) {
        return this.stockQuantity >= requestedQuantity;
    }
}