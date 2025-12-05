package com.example.market_api.core.contact_type.mapper;

import org.springframework.stereotype.Component;

import com.example.market_api.core.contact_type.dto.ContactTypeForm;
import com.example.market_api.core.contact_type.model.ContactType;

@Component
public class ContactTypeMapper {

    public ContactType toEntity(ContactTypeForm contactTypeForm) {
        return ContactType.builder()
                .channel(contactTypeForm.getChannel())
                .baseUrl(contactTypeForm.getBaseUrl())
                .validationRegex(contactTypeForm.getValidationRegex())
                .iconUrl(contactTypeForm.getIconUrl())
                .build();
    }
}
