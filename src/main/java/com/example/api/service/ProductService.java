package com.example.api.service;


import com.example.api.exception.DuplicateResourceException;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.dto.ProductReq;
import com.example.api.model.dto.StockQuantityUpdateRequest;
import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> getProducts(Boolean isAvailable, Pageable pageable){
        Page<Product> productPage;
        if(isAvailable != null){
            productPage = productRepository.findByIsAvailable(isAvailable, pageable);
        }else{
            productPage = productRepository.findAll(pageable);
        }
        return productPage;
    }

    public Product getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Khong tim thay san pham voi ID: " + id));
        return product;
    }
    public Product updateProduct(Long id, ProductReq dto){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Khong tim thay san pham  voi ID: " + id));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Khong tim thay danh muc voi id: " + dto.getCategoryId()));
        if(!product.getSku().equals(dto.getSku()) && productRepository.existsBySku(dto.getSku())){
            throw new DuplicateResourceException("Ma SKU" + dto.getSku()+ " da ton tai");
        }
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setIsAvailable(dto.getIsAvailable());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateStockQuantity(Long id, StockQuantityUpdateRequest dto){
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        product.setStockQuantity(dto.getStockQuantity());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public Product createProductForCategory(Long categoryId, ProductReq dto){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
        if(productRepository.existsBySku(dto.getSku())){
            throw new DuplicateResourceException("Ma SKU " + dto.getSku() + "da ton tai");
        }
        Product product = Product.builder()
                .sku(dto.getSku())
                .name(dto.getName())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .isAvailable(dto.getIsAvailable())
                .category(category)
                .build();
        return productRepository.save(product);
    }
}
