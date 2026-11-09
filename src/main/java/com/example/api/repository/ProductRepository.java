package com.example.api.repository;

import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
    Page<Product> findByIsAvailable(Boolean isAvailable, Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);
}
