package com.target.myRetail.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myRetail.models.Product;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedSkyTargetClient redSkyTargetClient;

    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findBy_id(productId);
        ProductResponse productResponse = ProductResponse.transformProductToProductResponse(product);
        String name = getProductTitle(productId);
        productResponse.setName(name);
        return productResponse;
    }

    private String getProductTitle(Integer productId) {
        HashMap<String, Map> productInfoMap = getProductInfoByProductId(productId);
        Map<String,Map> productMap = productInfoMap.get("product");
        Map<String,Map> itemMap = productMap.get("item");
        Map<String,String> prodDescrMap = itemMap.get(("product_description"));
        return prodDescrMap.get("title");
    }

    private HashMap<String, Map> getProductInfoByProductId(Integer productId) {
        ResponseEntity<String> productInfoClientResponse = redSkyTargetClient.getProductInfoById(productId.toString());
        HashMap<String, Map> productInfoMap = new HashMap<>();
        try {
            productInfoMap = new ObjectMapper().readValue(productInfoClientResponse.getBody(), HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return productInfoMap;
    }
}
