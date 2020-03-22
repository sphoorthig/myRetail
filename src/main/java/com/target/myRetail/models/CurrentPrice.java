package com.target.myRetail.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentPrice {
    Double value;
    String currency_code;
}
