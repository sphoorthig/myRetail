package com.target.myRetail.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @ApiModelProperty(notes = "Product Id", dataType = "Integer", example = "123456")
    private Integer id;
    @ApiModelProperty(notes = "The name of the product")
    private String name;
    @ApiModelProperty(notes = "The price details of the product")
    private CurrentPrice current_price;

}