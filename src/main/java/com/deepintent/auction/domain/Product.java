package com.deepintent.auction.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private String address;
    private BigDecimal price;

}
