package com.example.market_api.core.payment_method.model;

import java.util.Set;

import com.example.market_api.common.base.AuditableEntity;
import com.example.market_api.core.profile.model.company.CompanyProfile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "paymentMethods")
    private Set<CompanyProfile> companyProfiles;

    public void addCompanyProfile(CompanyProfile companyProfile) {
        if (companyProfile != null && !this.companyProfiles.contains(companyProfile)) {
            this.companyProfiles.add(companyProfile);
        }
    }
    
    public void removeCompanyProfile(CompanyProfile companyProfile) {
        if (companyProfile != null && this.companyProfiles.contains(companyProfile)) {
            this.companyProfiles.remove(companyProfile);
        }
    }
}
