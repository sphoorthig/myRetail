package com.target.myRetail.controller;

import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductResponse getProductById(int productId) {
        return productService.getProductById(productId);
    }
}
