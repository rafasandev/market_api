package com.example.market_api.core.payment_method.model;

import com.example.market_api.common.base.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payment_methods")
@Getter
@SuperBuilder
@NoArgsConstructor
public class PaymentMethod extends AuditableEntity {
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String iconUrl;
}
