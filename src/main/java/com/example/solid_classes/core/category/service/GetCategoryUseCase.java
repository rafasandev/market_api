package com.example.solid_classes.core.category.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.core.category.dto.CategoryResponseDto;
import com.example.solid_classes.core.category.mapper.CategoryMapper;
import com.example.solid_classes.core.category.model.Category;
import com.example.solid_classes.core.profile.model.company.enums.BusinessSector;

import lombok.RequiredArgsConstructor;

/**
 * UseCase para operações de consulta de categorias.
 * Responsável por buscar categorias via Service e convertê-las em DTOs.
 */
@Service
@RequiredArgsConstructor
public class GetCategoryUseCase {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
            .map(categoryMapper::toResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(UUID id) {
        Category category = categoryService.getById(id);
        return categoryMapper.toResponseDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoriesByBusinessSector(BusinessSector businessSector) {
        List<Category> categories = categoryService.findByBusinessSector(businessSector);
        return categories.stream()
            .map(categoryMapper::toResponseDto)
            .toList();
    }
}
