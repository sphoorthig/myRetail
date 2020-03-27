package com.target.myRetail.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
@Builder
@Data
public class ProductEntity {
    @Id
    private Integer _id;
    private CurrentPrice current_price;
}
