package com.example.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private Boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
