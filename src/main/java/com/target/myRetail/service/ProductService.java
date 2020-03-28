package com.target.myRetail.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import com.target.myRetail.transformers.ProductTransformer;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public ProductResponse getProductById(Integer productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        String name = getProductTitle(productId);

        if (!productEntity.isPresent() || name == null) {
            throw new ProductNotFoundException("Product not found");
        }
        ProductResponse productResponse = ProductTransformer.transformProductEntityToProductResponse(productEntity.get());
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
            return null;
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
        return null;
    }

    public void updateProduct(UpdateProductRequest updateProductRequest, Integer productId) {
        Optional<ProductEntity> retrieveProductEntity = productRepository.findById(productId);

        if (!retrieveProductEntity.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        ProductEntity saveProductEntity = ProductTransformer.transformUpdateProductRequestToProductEntity(updateProductRequest, productId);
        productRepository.save(saveProductEntity);
    }
}
