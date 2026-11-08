package com.example.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductReq {
    @NotBlank
    @Size(min = 8, max = 12)
    private String sku;
    @NotBlank
    @Size(min = 5, max = 200)
    private String name;
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer stockQuantity;
    @NotNull
    private Boolean isAvailable;
    @NotNull
    private Long categoryId;
}
