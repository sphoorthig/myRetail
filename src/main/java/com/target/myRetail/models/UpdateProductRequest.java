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
public class UpdateProductRequest {
    @ApiModelProperty(notes = "The price details of the product")
    private CurrentPrice current_price;
}
