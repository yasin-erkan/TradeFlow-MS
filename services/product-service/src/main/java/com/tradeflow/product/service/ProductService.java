package com.tradeflow.product.service;

import com.tradeflow.product.dto.ProductRequest;
import com.tradeflow.product.dto.ProductPurchaseRequest;
import com.tradeflow.product.dto.ProductPurchaseResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.tradeflow.product.dto.ProductResponse;
import com.tradeflow.product.repository.ProductRepository;
import com.tradeflow.product.mapper.ProductMapper;
import com.tradeflow.product.exception.ProductPurchaseException;
import com.tradeflow.product.model.Product;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request){
        var product = mapper.toProduct(request);
       return  repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request){

      var productIds = request.stream().map(ProductPurchaseRequest::getProductId).toList();

      var storedProducts = repository.findAllByIdInOrderById(productIds);
      if (storedProducts.size() != productIds.size()) {
        throw new ProductPurchaseException("One or more products not found");
      }
     
     var storeRequest = request
     .stream()
     .sorted(Comparator.comparing(ProductPurchaseRequest::getProductId))
     .toList();
    var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

    for (int i = 0; i < storedProducts.size(); i++) { 

        var product = storedProducts.get(i);
        var productRequest = storeRequest.get(i);
        if (product.getAvailableQuantity() < productRequest.getQuantity()) {
            throw new ProductPurchaseException(String.format("insufficient quantity for product with ID: %s", productRequest.getProductId()) );  
        }

       var newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
       product.setAvailableQuantity(newAvailableQuantity);
       repository.save(product);

       purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.getQuantity()));
      
    }
    return purchasedProducts;    
    }


    public ProductResponse findById(Integer productId){
        return repository.findById(productId)
        .map(mapper::toProductResponse)
        .orElseThrow(() -> new com.tradeflow.product.exception.ProductNotFoundException(
            String.format("Cannot find product with id %s :: No Product Found", productId)
        ));
    }

    public List<ProductResponse> findAll(){
        return repository.findAll()
        .stream()
        .map(mapper::toProductResponse)
        .collect(Collectors.toList())   ;
    }

}