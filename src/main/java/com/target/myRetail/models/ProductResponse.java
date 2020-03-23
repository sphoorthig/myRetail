package com.target.myRetail.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {
    private Integer id;
    private String name;
    private CurrentPrice current_price;

    public static ProductResponse transformProductToProductResponse(Product product) {
        return ProductResponse
                .builder()
                .id(product.get_id())
                .current_price(product.getCurrent_price())
                .build();
    }
}