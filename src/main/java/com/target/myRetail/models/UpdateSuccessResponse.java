package com.target.myRetail.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateSuccessResponse {
    @ApiModelProperty(notes = "Http Status value of the update operation", example = "200")
    int status;
    @ApiModelProperty(notes = "Message of update operation", example = "Product updated successfully")
    String message;
}
