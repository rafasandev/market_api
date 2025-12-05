package com.example.market_api.core.contact_info.mapper;

import org.springframework.stereotype.Component;

import com.example.market_api.core.contact_info.dto.ContactInfoForm;
import com.example.market_api.core.contact_info.model.ContactInfo;

@Component
public class ContactTypeMapper {
    
    public ContactInfo toEntity(ContactInfoForm contactForm) {
        ContactInfo contactInfo = ContactInfo.builder()
            .name(contactForm.getName())
    }
}
