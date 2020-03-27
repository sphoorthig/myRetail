package com.target.myRetail.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductList {
    private Integer id;
    private String name;
    private CurrentPrice current_price;

    public static ProductList transformProductToProductResponse(ProductEntity product) {
        return ProductList
                .builder()
                .id(product.get_id())
                .current_price(product.getCurrent_price())
                .build();
    }
}