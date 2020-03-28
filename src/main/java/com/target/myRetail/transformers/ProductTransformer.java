package com.target.myRetail.transformers;

import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.ProductEntity;

public class ProductTransformer {
    public static ProductResponse transformProductEntityToProductResponse(ProductEntity productEntity) {
        return ProductResponse
                .builder()
                .id(productEntity.get_id())
                .current_price(productEntity.getCurrent_price())
                .build();
    }

    public static ProductEntity transformProductToProductEntity(ProductResponse productResponse) {
        return ProductEntity
                .builder()
                ._id(productResponse.getId())
                .current_price(productResponse.getCurrent_price())
                .build();
    }
}
