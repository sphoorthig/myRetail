package com.target.myRetail.utils;

import com.target.myRetail.models.CurrentPrice;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;

public class TestUtils {
    public static int productId = 123456;

    public static ProductResponse getMockProductResponse() {
        return ProductResponse
                .builder()
                .id(productId)
                .name("Test Product Name")
                .current_price(CurrentPrice
                        .builder()
                        .currency_code("USD")
                        .value(13.46)
                        .build())
                .build();
    }

    public static ProductEntity getMockProductEntity() {
        return ProductEntity
                .builder()
                ._id(productId)
                .current_price(CurrentPrice
                        .builder()
                        .currency_code("USD")
                        .value(13.46)
                        .build())
                .build();
    }
}
