package com.target.myRetail.controller;

import com.target.myRetail.models.Product;
import com.target.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Integer id) {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok().body(product);

    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProductById(@PathVariable(value = "id") Integer id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok().body(product);

    }
}
