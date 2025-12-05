package com.example.market_api.core.contact_info.model;

import com.example.market_api.common.base.AuditableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contact_methods")
@Getter
@SuperBuilder
@NoArgsConstructor
public class ContactMethod extends AuditableEntity{
    
}
