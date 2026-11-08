package com.example.api.service;

import com.example.api.exception.DuplicateResourceException;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.dto.CategoryReq;
import com.example.api.model.dto.ProductReq;
import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
        return category;
    }
    public Category createCategory(CategoryReq dto){
        if(categoryRepository.existsByCode(dto.getCode())){
            throw new DuplicateResourceException("Ma danh muc " + dto.getCode() +"da ton tai");
        }
        Category category = Category.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return categoryRepository.save(category);
    }
    public Category updateCategory(Long id, CategoryReq dto){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
        if(!category.getCode().equals(dto.getCode()) && categoryRepository.existsByCode(dto.getCode())){
            throw new DuplicateResourceException("Ma danh muc " + dto.getCode() + "da ton tai");
        }
        category.setCode(dto.getCode());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
        categoryRepository.delete(category);
    }

    public List<Product> getProductsByCategoryId(Long categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException("Category not found!");
        }
        return productRepository.findByCategoryId(categoryId);
    }


}

