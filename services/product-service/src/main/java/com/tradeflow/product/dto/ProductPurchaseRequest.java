package com.tradeflow.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseRequest {
        @NotNull(message = "Product id is required")
        private Integer productId;
        
        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity must be positive")
        private Double quantity;
}