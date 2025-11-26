package com.example.solid_classes.core.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.solid_classes.core.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    
    List<Product> findByCompanyId(UUID companyId);
    List<Product> findByCategoryId(UUID categoryId);
}
