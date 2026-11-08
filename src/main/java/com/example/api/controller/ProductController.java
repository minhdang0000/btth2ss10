package com.example.api.controller;

import com.example.api.model.dto.ProductReq;
import com.example.api.model.dto.StockQuantityUpdateRequest;
import com.example.api.model.entity.Product;
import com.example.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Boolean isAvailable,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return ResponseEntity.ok(productService.getProducts(isAvailable, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductReq req){
        return new ResponseEntity<>(
          productService.createProductForCategory(req.getCategoryId(), req), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductReq req){
        return ResponseEntity.ok(productService.updateProduct(id, req));
    }

    @PatchMapping("/{id}/stock-quantity")
    public ResponseEntity<Product> updateStockQuantity(@PathVariable Long id, @Valid @RequestBody StockQuantityUpdateRequest req){
        return ResponseEntity.ok(productService.updateStockQuantity(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
