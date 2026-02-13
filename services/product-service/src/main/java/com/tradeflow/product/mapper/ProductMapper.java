package com.tradeflow.product.mapper;

import org.springframework.stereotype.Service;
import com.tradeflow.product.dto.ProductRequest;
import com.tradeflow.product.dto.ProductResponse;
import com.tradeflow.product.model.Product;
import com.tradeflow.product.dto.ProductPurchaseResponse;
import com.tradeflow.product.model.Category;


@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
        .id(request.id())
        .name(request.name())
        .description(request.description())
        .price(request.price())
        .availableQuantity(request.availableQuantity())
        .category(
            Category.builder()
            .id(request.categoryId())
            .build())
        .build();
}



public ProductResponse toProductResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getAvailableQuantity(),
        product.getPrice(),
        product.getCategory().getId(),
        product.getCategory().getName(),
        product.getCategory().getDescription()
    );
}

public ProductPurchaseResponse toProductPurchaseResponse(Product product, Double quantity) {

    return new ProductPurchaseResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        quantity
    );
}
}
