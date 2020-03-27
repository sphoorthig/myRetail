package com.target.myRetail.controller;

import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
