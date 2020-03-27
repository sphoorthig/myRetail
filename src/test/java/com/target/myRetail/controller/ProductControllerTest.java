package com.target.myRetail.controller;

import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.Product;
import com.target.myRetail.service.ProductService;
import com.target.myRetail.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProductById_ReturnsProductDetails_forValid() {
        Product product = TestUtils.getMockProductResponse();
        when(productService.getProductById(TestUtils.productId)).thenReturn(product);
        ResponseEntity<Product> actualResponse = productController.getProductById(TestUtils.productId);

        assertThat(actualResponse.getBody()).isEqualTo(product);
    }

    @Test
    public void getProductById_ReturnsEmptyResponse_forProductNotFoundInDataStore() {
        when(productService.getProductById(TestUtils.productId)).thenThrow(new ProductNotFoundException("Product not found"));
        assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductById(TestUtils.productId);
        });
    }

}