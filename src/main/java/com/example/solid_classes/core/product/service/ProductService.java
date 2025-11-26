package com.example.solid_classes.core.product.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.solid_classes.core.product.model.Product;
import com.example.solid_classes.core.product.ports.ProductPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductPort productPort;

    public Product getById(UUID id) {
        return productPort.getById(id);  // CORREÇÃO: Bug de recursão infinita!
    }

    public Product createProduct(Product newProduct) {
        return productPort.save(newProduct);
    }

    public java.util.List<Product> getAllProducts() {
        return productPort.findAll();
    }

    public java.util.List<Product> getProductsByCompanyId(UUID companyId) {
        return productPort.findByCompanyId(companyId);
    }

    public java.util.List<Product> getProductsByCategoryId(UUID categoryId) {
        return productPort.findByCategoryId(categoryId);
    }
}
