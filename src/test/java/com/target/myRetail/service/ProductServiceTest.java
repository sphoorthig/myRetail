package com.target.myRetail.service;

import com.target.myRetail.models.CurrentPrice;
import com.target.myRetail.models.Product;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    private int productId = 123456;
    @Test
    public void getProductById_returnsProductInfo() {
        Product productEntity = Product
                .builder()
                .productId(productId)
                .currentPrice(CurrentPrice
                        .builder()
                        .currency_code("USD")
                        .value(13.46)
                        .build())
                .build();

        when(productRepository.findByProductId(productId)).thenReturn(productEntity);
        ProductResponse productResponse = productService.getProductById(productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.getProductId());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrentPrice().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrentPrice().getCurrency_code());
    }

}