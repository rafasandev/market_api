package com.example.solid_classes.core.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.solid_classes.core.category.dto.CategoryForm;
import com.example.solid_classes.core.category.dto.CategoryResponseDto;
import com.example.solid_classes.core.category.service.RegisterCategoryUseCase;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final RegisterCategoryUseCase registerCategoryUseCase;
    private final com.example.solid_classes.core.category.service.CategoryService categoryService;
    private final com.example.solid_classes.core.category.mapper.CategoryMapper categoryMapper;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN_MASTER')")
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryForm categoryForm) {
        CategoryResponseDto newCategory = registerCategoryUseCase.registerCategory(categoryForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public ResponseEntity<java.util.List<CategoryResponseDto>> getAllCategories() {
        java.util.List<com.example.solid_classes.core.category.model.Category> categories = 
            categoryService.getAllCategories();
        java.util.List<CategoryResponseDto> response = categories.stream()
            .map(categoryMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID id) {
        com.example.solid_classes.core.category.model.Category category = categoryService.getById(id);
        CategoryResponseDto response = categoryMapper.toResponseDto(category);
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/sector/{businessSector}")
    public ResponseEntity<java.util.List<CategoryResponseDto>> getCategoriesByBusinessSector(
            @org.springframework.web.bind.annotation.PathVariable 
            com.example.solid_classes.core.profile.model.company.enums.BusinessSector businessSector) {
        java.util.List<com.example.solid_classes.core.category.model.Category> categories = 
            categoryService.getCategoriesByBusinessSector(businessSector);
        java.util.List<CategoryResponseDto> response = categories.stream()
            .map(categoryMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }
}
