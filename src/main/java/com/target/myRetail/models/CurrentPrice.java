package com.target.myRetail.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CurrentPrice {
    @NotEmpty
    Double value;
    @NotEmpty
    String currency_code;
}
