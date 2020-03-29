package com.target.myRetail.service;

import com.target.myRetail.exception.ProductNotFoundException;
import com.target.myRetail.models.ProductEntity;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.redskyresource.RedSkyTargetClient;
import com.target.myRetail.repository.ProductRepository;
import com.target.myRetail.utils.TestUtils;
import feign.FeignException;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Mock
    RedSkyTargetClient redSkyTargetClient;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProductById_returnsProductInfo() throws IOException {
        ProductEntity productEntity = TestUtils.getMockProductEntity();

        when(productRepository.findById(TestUtils.productId)).thenReturn(Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(TestUtils.productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyresponse.json"))));

        ProductResponse productResponse = productService.getProductById(TestUtils.productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrent_price().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrent_price().getCurrency_code());
        assertThat(productResponse.getName()).isEqualTo("The Big Lebowski (Blu-ray)");
    }

    @Test
    public void getProductById_returnsProductInfo_withEmptyResponse_whenRedSkySourceReturnsEmptyTitle() throws IOException {
        ProductEntity productEntity = TestUtils.getMockProductEntity();

        when(productRepository.findById(TestUtils.productId)).thenReturn(Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(TestUtils.productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyResponseEmptyTitle.json"))));

        ProductResponse productResponse = productService.getProductById(TestUtils.productId);
        assertThat(productResponse.getId()).isEqualTo(productEntity.get_id());
        assertThat(productResponse.getCurrent_price().getValue()).isEqualTo(productEntity.getCurrent_price().getValue());
        assertThat(productResponse.getCurrent_price().getCurrency_code()).isEqualTo(productEntity.getCurrent_price().getCurrency_code());
        assertThat(productResponse.getName()).isEmpty();
    }

    @Test
    public void getProductById_throwsProductNotFoundException_whenProductNotFoundInDataBase() throws IOException {
        when(productRepository.findById(TestUtils.productId)).thenReturn(Optional.empty());
        when(redSkyTargetClient.getProductInfoById(TestUtils.productId.toString()))
                .thenReturn(ResponseEntity.ok
                        (FileUtil.readAsString(new File("src/test/java/com/target/myRetail/responses/redskyresponse.json"))));

        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(TestUtils.productId);
        });
    }

    @Test
    public void getProductById_throwsProductNotFoundException_whenRedSkySourceThrows404NotFound() {
        ProductEntity productEntity = TestUtils.getMockProductEntity();
        when(productRepository.findById(TestUtils.productId)).thenReturn(java.util.Optional.ofNullable(productEntity));
        when(redSkyTargetClient.getProductInfoById(TestUtils.productId.toString())).thenThrow(FeignException.class);
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(TestUtils.productId);
        });
    }

    @Test
    public void updateProduct_savesTheProductToDB() {
        UpdateProductRequest updateProductRequest = TestUtils.getUpdateProductRequest();
        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepository.findById(any())).thenReturn(Optional.ofNullable(TestUtils.getMockProductEntity()));
        when(productRepository.save(captor.capture())).thenReturn(any());

        productService.updateProduct(updateProductRequest, TestUtils.productId);

        verify(productRepository).save(any(ProductEntity.class));
        assertThat(captor.getValue().get_id()).isEqualTo(TestUtils.productId);
        assertThat(captor.getValue().getCurrent_price()).isEqualTo(updateProductRequest.getCurrent_price());
    }

    @Test
    public void updateProduct_throwsProductNotFoundException_whenProductDoesNotExistInDB() {
        UpdateProductRequest updateProductRequest = TestUtils.getUpdateProductRequest();
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(updateProductRequest, TestUtils.productId);
        });
    }
}