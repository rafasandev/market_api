package com.example.market_api.core.contact_type.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.market_api.core.contact_type.model.ContactType;

public interface ContactTypeRepository extends JpaRepository<ContactType, UUID>{
    
}
