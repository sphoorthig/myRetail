package com.target.myRetail.service;

import com.target.myRetail.models.Product;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findByProductId(productId);
        return ProductResponse.transformProductToProductResponse(product);
    }
}
