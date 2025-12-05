package com.example.market_api.core.contact_type.service;

import org.springframework.stereotype.Component;

import com.example.market_api.common.base.NamedCrudAdapter;
import com.example.market_api.core.contact_type.model.ContactType;
import com.example.market_api.core.contact_type.ports.ContactTypePort;
import com.example.market_api.core.contact_type.repository.ContactTypeRepository;

@Component
public class ContactTypeAdapter extends NamedCrudAdapter <ContactType, ContactTypeRepository> implements ContactTypePort {

    public ContactTypeAdapter(ContactTypeRepository repository) {
        super(repository, "Tipo de Canal de Contato");
    }
    
}
