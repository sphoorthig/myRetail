package com.target.myRetail.transformers;

import com.target.myRetail.models.CurrentPrice;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;

public class ProductTransformer {

    public static final String DEFAULT_CURRENCY_CODE = "USD";

    public static ProductResponse transformProductEntityToProductResponse(ProductEntity productEntity) {
        return ProductResponse
                .builder()
                .id(productEntity.get_id())
                .current_price(productEntity.getCurrent_price())
                .build();
    }

    public static ProductEntity transformUpdateProductRequestToProductEntity(UpdateProductRequest updateProductRequest, Integer productId) {
        return ProductEntity
                .builder()
                ._id(productId)
                .current_price(CurrentPrice
                        .builder()
                        .value(updateProductRequest.getCurrent_price().getValue())
                        .currency_code(updateProductRequest.getCurrent_price().getCurrency_code())
                        .build())
                .build();
    }
}
