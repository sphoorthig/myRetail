package com.target.myRetail.models;

import com.target.myRetail.models.CurrentPrice;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Builder
@Data
public class Product {
    @Id
    private Integer productId;
    private CurrentPrice currentPrice;
}
