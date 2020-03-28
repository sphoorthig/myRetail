package com.target.myRetail.controller;

import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") Integer id) {
            ProductResponse productResponse = productService.getProductById(id);
            return ResponseEntity.ok().body(productResponse);

    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable(value = "id") Integer id,
                                                             @RequestBody ProductResponse productResponse) {

        return ResponseEntity.ok().body(productService.updateProduct(productResponse));
    }
}
