package com.target.myRetail.service;

import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import com.target.myRetail.utils.TestUtils;
import feign.FeignException;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Mock
    RedSkyTargetClient redSkyTargetClient;

    private Integer productId = 123456;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProductById_returnsProductInfo() throws IOException {
        ProductEntity productEntity = TestUtils.getMockProductEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyresponse.json"))));

        ProductResponse productResponse = productService.getProductById(productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrent_price().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrent_price().getCurrency_code());
        assertThat(productResponse.getName()).isEqualTo("The Big Lebowski (Blu-ray)");
    }

    @Test
    public void getProductById_returnsProductInfo_withEmptyResponse() throws IOException {
        ProductEntity productEntity = TestUtils.getMockProductEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyResponseEmptyTitle.json"))));

        ProductResponse productResponse = productService.getProductById(productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrent_price().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrent_price().getCurrency_code());
        assertThat(productResponse.getName()).isEmpty();
    }

    @Test
    public void getProductById_throwsProductNotFoundException_whenProductNotFoundInDataBase() throws IOException {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        when(redSkyTargetClient.getProductInfoById(productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyresponse.json"))));

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    public void getProductById_throwsProductNotFoundException_whenRedSkySourceThrows404NotFound() {
        ProductEntity productEntity = TestUtils.getMockProductEntity();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(productId.toString())).thenThrow(FeignException.class);
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

}