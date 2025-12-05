package com.example.market_api.core.profile.model.value;

import com.example.market_api.core.profile.model.enums.ContactChannel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContactMethod {

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_channel", nullable = false, length = 30)
    private ContactChannel channel;

    @Column(name = "contact_value", nullable = false, length = 80)
    private String value;
}
