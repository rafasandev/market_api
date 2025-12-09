package com.example.market_api.core.profile.dto.company;

import com.example.market_api.core.profile.model.company.enums.BusinessSector;
import com.example.market_api.core.user.dto.UserRegisterForm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CompanyProfileForm extends UserRegisterForm {

    @NotEmpty(message = "O nome da empresa não pode estar vazio")
    private String companyName;

    @NotEmpty(message = "O CNPJ deve ser informado")
    private String cnpj;

    @NotNull(message = "O ramo de atividade deve ser informado")
    private BusinessSector businessSector;

    @NotNull(message = "Deve ser informado o local de referência de venda/serviço")
    private String locationReference;
}
