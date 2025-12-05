package com.example.market_api.core.contact_type.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.market_api.core.contact_type.model.ContactType;
import com.example.market_api.core.contact_type.ports.ContactTypePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactTypeService {

    private final ContactTypePort contactTypePort;

    public ContactType getById(UUID id) {
        return contactTypePort.getById(id);
    }
}
