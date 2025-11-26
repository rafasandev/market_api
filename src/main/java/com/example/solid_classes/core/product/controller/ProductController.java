package com.example.solid_classes.core.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.example.solid_classes.core.product.dto.ProductForm;
import com.example.solid_classes.core.product.dto.ProductResponseDto;
import com.example.solid_classes.core.product.service.RegisterProductUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final RegisterProductUseCase registerProductUseCase;
    private final com.example.solid_classes.core.product.service.ProductService productService;
    private final com.example.solid_classes.core.product.mapper.ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductForm productForm) {
        ProductResponseDto productResponse = registerProductUseCase.registerProduct(productForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public ResponseEntity<java.util.List<ProductResponseDto>> getAllProducts() {
        java.util.List<com.example.solid_classes.core.product.model.Product> products = productService.getAllProducts();
        java.util.List<ProductResponseDto> response = products.stream()
            .map(productMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@org.springframework.web.bind.annotation.PathVariable java.util.UUID id) {
        com.example.solid_classes.core.product.model.Product product = productService.getById(id);
        ProductResponseDto response = productMapper.toResponseDto(product);
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/company/{companyId}")
    public ResponseEntity<java.util.List<ProductResponseDto>> getProductsByCompany(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID companyId) {
        java.util.List<com.example.solid_classes.core.product.model.Product> products = 
            productService.getProductsByCompanyId(companyId);
        java.util.List<ProductResponseDto> response = products.stream()
            .map(productMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.GetMapping("/category/{categoryId}")
    public ResponseEntity<java.util.List<ProductResponseDto>> getProductsByCategory(
            @org.springframework.web.bind.annotation.PathVariable java.util.UUID categoryId) {
        java.util.List<com.example.solid_classes.core.product.model.Product> products = 
            productService.getProductsByCategoryId(categoryId);
        java.util.List<ProductResponseDto> response = products.stream()
            .map(productMapper::toResponseDto)
            .toList();
        return ResponseEntity.ok(response);
    }
}
