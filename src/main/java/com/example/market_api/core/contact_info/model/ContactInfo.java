package com.example.market_api.core.contact_info.model;

import com.example.market_api.common.base.AuditableEntity;
import com.example.market_api.core.contact_info.model.enums.ContactChannel;
import com.example.market_api.core.profile.model.ProfileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contact_types")
@SuperBuilder
@Getter
@NoArgsConstructor
public class ContactInfo extends AuditableEntity {
    
    @Column(nullable = false, unique = true)
    private ContactChannel channel;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private String baseUrl;

    @Column(nullable = false)
    private String validationRegex;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
