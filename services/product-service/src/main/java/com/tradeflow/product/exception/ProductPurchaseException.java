package com.tradeflow.product.exception;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String message) {
        super(message);
    }
}