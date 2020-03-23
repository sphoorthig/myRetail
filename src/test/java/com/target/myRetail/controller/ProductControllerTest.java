package com.target.myRetail.controller;

import com.target.myRetail.models.CurrentPrice;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    ProductController productController;

    @MockBean
    ProductService productService;

    private int productId = 123456;

    @Test
    public void getProductById_ReturnsProductDetails_forValid() {
        ProductResponse productResponse = ProductResponse
                .builder()
                .id(productId)
                .name("Test Product Name")
                .current_price(CurrentPrice
                        .builder()
                        .currency_code("USD")
                        .value(13.46)
                        .build())
                .build();
        when(productService.getProductById(productId)).thenReturn(productResponse);
        ProductResponse actualResponse = productController.getProductById(productId);

        assertThat(actualResponse).isEqualTo(productResponse);
    }

}