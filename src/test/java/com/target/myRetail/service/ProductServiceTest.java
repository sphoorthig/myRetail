package com.target.myRetail.service;

import com.target.myRetail.models.CurrentPrice;
import com.target.myRetail.models.Product;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @MockBean
    RedSkyTargetClient redSkyTargetClient;

    private Integer productId = 123456;
    @Test
    public void getProductById_returnsProductInfo() {
        Product productEntity = Product
                .builder()
                ._id(productId)
                .current_price(CurrentPrice
                        .builder()
                        .currency_code("USD")
                        .value(13.46)
                        .build())
                .build();

        when(productRepository.findBy_id(productId)).thenReturn(productEntity);
        when(redSkyTargetClient.getProductInfoById(productId.toString())).thenReturn(ResponseEntity.ok("{\n" +
                "   \"product\":{\n" +
                "      \"item\":{\n" +
                "         \"tcin\":\"13860428\",\n" +
                "         \"product_description\":{\n" +
                "            \"title\":\"The Big Lebowski (Blu-ray)\"\n" +
                "         }\n" +
                "   }\n" +
                "}\n" +
                "}"));
        ProductResponse productResponse = productService.getProductById(productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrent_price().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrent_price().getCurrency_code());
        assertThat(productResponse.getName()).isEqualTo("The Big Lebowski (Blu-ray)");
    }

}