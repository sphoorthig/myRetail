package com.target.myRetail.models;

import com.target.myRetail.models.CurrentPrice;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
public class Product {
    @Id
    private Integer _id;
    private CurrentPrice current_price;
}
