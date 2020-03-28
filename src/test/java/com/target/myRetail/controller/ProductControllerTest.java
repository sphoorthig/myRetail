package com.target.myRetail.controller;

import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.exception.UpdateRequestNotValidException;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.models.UpdateSuccessResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    public void getProductById_ReturnsProductDetails_forValidProductId() {
        ProductResponse productResponse = TestUtils.getMockProductResponse();
        when(productService.getProductById(TestUtils.productId)).thenReturn(productResponse);
        ResponseEntity<ProductResponse> actualResponse = productController.getProductById(TestUtils.productId);

        assertThat(actualResponse.getBody()).isEqualTo(productResponse);
    }

    @Test
    public void getProductById_throwsProductNotFound_whenProductNotFoundInDataStore() {
        when(productService.getProductById(TestUtils.productId)).thenThrow(new ProductNotFoundException("Product not found"));
        assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductById(TestUtils.productId);
        });
    }

    @Test
    public void updateProductById_ReturnsProductDetails_forValidProductIdAndRequest() {
        UpdateProductRequest updateProductRequest = TestUtils.getUpdateProductRequest();
        doNothing().when(productService).updateProduct(any(), anyInt());
        ResponseEntity<UpdateSuccessResponse> actualResponse = productController.updateProductById(TestUtils.productId, updateProductRequest);
        assertThat(actualResponse.getBody().getMessage()).isEqualTo("Product updated successfully");
    }

    @Test
    public void updateProductById_throwsProductNotFound_whenProductNotFoundInDataStore() {
        doThrow(new ProductNotFoundException("Product not found")).when(productService).updateProduct(TestUtils.getUpdateProductRequest(), TestUtils.productId);
        assertThrows(ProductNotFoundException.class, () -> {
            productController.updateProductById(TestUtils.productId, TestUtils.getUpdateProductRequest());
        });
    }

    @Test
    public void updateProductById_throwsRequestNotValidException_whenRequestNotValid() {
        assertThrows(UpdateRequestNotValidException.class, () -> {
            productController.updateProductById(TestUtils.productId, TestUtils.getInvalidUpdateProductRequest());
        });
    }
}