package com.target.myRetail.controller;

import com.target.myRetail.exception.UpdateRequestNotValidException;
import com.target.myRetail.models.ProductResponse;
import com.target.myRetail.models.UpdateProductRequest;
import com.target.myRetail.models.UpdateSuccessResponse;
import com.target.myRetail.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "Get the product information by product id", response = ProductResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product retrieved successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to access the endpoint"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The product you were trying to retrieve is not found in one or more data sources")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "id") Integer id) {
            ProductResponse productResponse = productService.getProductById(id);
            return ResponseEntity.ok().body(productResponse);

    }

    @ApiOperation(value = "Update the product price by product id", response = UpdateSuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product updated successfully"),
            @ApiResponse(code = 401, message = "You are not authorized to update the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The product you were trying to update is not found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateSuccessResponse> updateProductById(@PathVariable(value = "id") Integer id,
                                                                   @RequestBody UpdateProductRequest updateProductRequest) {
        if(StringUtils.isEmpty(updateProductRequest.getCurrent_price().getCurrency_code()) || updateProductRequest.getCurrent_price().getValue() == null){
            throw new UpdateRequestNotValidException("Update Request Not Valid - Currency code and Currency value must be provided");
        }
        productService.updateProduct(updateProductRequest, id);
        return ResponseEntity.ok().body(new UpdateSuccessResponse(HttpStatus.OK.value(), "Product updated successfully"));
    }
}
