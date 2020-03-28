package com.target.myRetail.controller;

import com.target.myRetail.exception.UpdateRequestNotValidException;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.models.UpdateSuccessResponse;
import com.target.myRetail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") Integer id) {
            ProductResponse productResponse = productService.getProductById(id);
            return ResponseEntity.ok().body(productResponse);

    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateSuccessResponse> updateProductById(@PathVariable(value = "id") Integer id,
                                                                   @RequestBody UpdateProductRequest updateProductRequest) {
        if(StringUtils.isEmpty(updateProductRequest.getCurrent_price().getCurrency_code()) || updateProductRequest.getCurrent_price().getValue() == null){
            throw new UpdateRequestNotValidException("Update Request Not Valid - Currency code and Currency value must be provided");
        }
        productService.updateProduct(updateProductRequest, id);
        return ResponseEntity.ok().body(new UpdateSuccessResponse(HttpStatus.OK.value(), "Product updated successfully"));
    }
}
