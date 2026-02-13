package com.tradeflow.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        
        @NotNull(message = "Product name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
        
        @NotNull(message = "Product Description is required")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description,
        
        @NotNull(message = "Product Available quantity should be positive")
        @Positive(message = "Available quantity must be positive")
        Double availableQuantity,
        
        @NotNull(message = "Price should be greater than 0")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,
        
        @NotNull(message = "Product Category is ")
        Integer categoryId
) {
}
