package com.target.myRetail.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.Product;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedSkyTargetClient redSkyTargetClient;

    public Product getProductById(Integer productId) {
        Optional<ProductEntity> product = productRepository.findById(productId);
        Product productResponse = new Product();
        if (product.isPresent()) {
            productResponse = Product.transformProductToProductResponse(product.get());
        } else {
            throw new ProductNotFoundException("Product not found");
        }

        String name = getProductTitle(productId);
        productResponse.setName(name);
        return productResponse;
    }

    private String getProductTitle(Integer productId) {
        ResponseEntity<String> productInfoClientResponse;
        HashMap<String, Map> productInfoMap = new HashMap<>();
        try {
            productInfoClientResponse = redSkyTargetClient.getProductInfoById(productId.toString());
            productInfoMap = new ObjectMapper().readValue(productInfoClientResponse.getBody(), new TypeReference<HashMap<String, Map>>() {
            });
            return getProductNameFromMap(productInfoMap);
        } catch (FeignException | JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new ProductNotFoundException("Product not found");
        }
    }

    private String getProductNameFromMap(HashMap<String, Map> productInfoMap) {
        Map<String, Map> productMap = productInfoMap.get("product");
        if (!productInfoMap.isEmpty()) {
            Map<String, Map> itemMap = productMap.get("item");
            if (!itemMap.isEmpty()) {
                Map<String, String> prodDescMap = itemMap.get(("product_description"));
                if (!prodDescMap.isEmpty()) {
                    return prodDescMap.get("title");
                }
            }
        }
        throw new ProductNotFoundException("Product not found");
    }
}
