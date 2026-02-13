package com.tradeflow.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;
import com.tradeflow.product.dto.ProductRequest;
import com.tradeflow.product.dto.ProductPurchaseRequest;
import com.tradeflow.product.dto.ProductPurchaseResponse;
import com.tradeflow.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import com.tradeflow.product.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService service;

   

   @PostMapping
   public ResponseEntity<Integer> createProduct(
    @RequestBody @Valid ProductRequest request 
   ) {
    return ResponseEntity.ok(service.createProduct(request));
   }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
        @RequestBody @Valid List<ProductPurchaseRequest> request ){
            return ResponseEntity.ok(service.purchaseProducts(request));
        }
@GetMapping("/{product-id}")
public ResponseEntity<ProductResponse> findById(
    @PathVariable("product-id") Integer productId
) {
    return ResponseEntity.ok(service.findById(productId));
}

@GetMapping
public ResponseEntity<List<ProductResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
}
    }
