package com.example.solid_classes.core.category.service;

import org.springframework.stereotype.Component;

import com.example.solid_classes.common.base.NamedCrudAdapter;
import com.example.solid_classes.core.category.model.Category;
import com.example.solid_classes.core.category.ports.CategoryPort;
import com.example.solid_classes.core.category.repository.CategoryRepository;
import com.example.solid_classes.core.profile.model.company.enums.BusinessSector;

@Component
public class CategoryAdapter extends NamedCrudAdapter<Category, CategoryRepository> implements CategoryPort {

    public CategoryAdapter(CategoryRepository categoryRepository) {
        super(categoryRepository, "Categoria");
    }

    @Override
    public java.util.List<Category> findByBusinessSector(BusinessSector businessSector) {
        return repository.findByBusinessSector(businessSector);
    }
}
