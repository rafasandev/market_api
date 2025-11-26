package com.example.solid_classes.core.category.ports;

import java.util.List;
import java.util.Optional;

import com.example.solid_classes.common.ports.NamedCrudPort;
import com.example.solid_classes.core.category.model.Category;
import com.example.solid_classes.core.profile.model.company.enums.BusinessSector;

public interface CategoryPort extends NamedCrudPort<Category>{
    List<Category> findByBusinessSector(BusinessSector businessSector);

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
}
