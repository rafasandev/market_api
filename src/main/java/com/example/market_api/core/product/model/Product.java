package com.example.market_api.core.product.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.market_api.common.base.AuditableMongoEntity;
import com.example.market_api.core.product_variation.model.ProductVariation;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Document(collection = "products")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Product extends AuditableMongoEntity {

    @Indexed(unique = true)
    private String productName;

    private String description;

    private BigDecimal basePrice;

    private int totalStock;

    @Indexed
    private UUID companyId;

    @Indexed
    private UUID categoryId;

    @DBRef(lazy = true)
    private List<ProductVariation> variations;

    public void addVariation(ProductVariation variation) {
        if (variation != null && this.variations != null && !this.variations.contains(variation)) {
            this.variations.add(variation);
            variation.setProductId(this.id);
        }
        recalculateTotalStock();
    }

    public void removeVariation(ProductVariation variation) {
        if (variation != null && this.variations != null) {
            this.variations.remove(variation);
            variation.setProductId(null);
        }
        recalculateTotalStock();
    }

    public boolean isAvailable() {
        return totalStock > 0;
    }

    public boolean productHasStock(int quantity) {
        return totalStock >= quantity;
    }

    public void recalculateTotalStock() {
        if (this.variations == null || this.variations.isEmpty()) {
            this.totalStock = 0;
            return;
        }

        this.totalStock = this.variations
                .stream()
                .mapToInt(ProductVariation::getStockQuantity)
                .sum();
    }

    public ProductVariation findVariation(UUID variationId) {
        return variations.stream()
                .filter(variation -> variation.getId().equals(variationId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Variação não encontrada"));
    }
}
