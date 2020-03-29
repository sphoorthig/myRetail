package com.target.myRetail.transformers;

import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTransformerTest {

    @Test
    void transformProductEntityToProductResponse() {
        ProductEntity productEntity = TestUtils.getMockProductEntity();
        ProductResponse productResponse = ProductTransformer.transformProductEntityToProductResponse(productEntity);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price()).isEqualTo(productEntity.getCurrent_price());
    }

    @Test
    void transformUpdateProductRequestToProductEntity() {
        UpdateProductRequest request = TestUtils.getUpdateProductRequest();
        ProductEntity productEntity = ProductTransformer.transformUpdateProductRequestToProductEntity(request, TestUtils.productId);
        assertThat(productEntity.get_id()).isEqualTo(TestUtils.productId);
        assertThat(productEntity.getCurrent_price()).isEqualTo(request.getCurrent_price());
    }
}