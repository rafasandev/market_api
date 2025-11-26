package com.example.solid_classes.core.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solid_classes.core.product.dto.ProductResponseDto;
import com.example.solid_classes.core.product.mapper.ProductMapper;
import com.example.solid_classes.core.product.model.Product;

import lombok.RequiredArgsConstructor;

/**
 * UseCase para operações de consulta de produtos.
 * Responsável por buscar produtos via Service e convertê-los em DTOs.
 */
@Service
@RequiredArgsConstructor
public class GetProductUseCase {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.findAll();
        return products.stream()
            .map(productMapper::toResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(UUID id) {
        Product product = productService.getById(id);
        return productMapper.toResponseDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCompanyId(UUID companyId) {
        List<Product> products = productService.findByCompanyId(companyId);
        return products.stream()
            .map(productMapper::toResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategoryId(UUID categoryId) {
        List<Product> products = productService.findByCategoryId(categoryId);
        return products.stream()
            .map(productMapper::toResponseDto)
            .toList();
    }
}
