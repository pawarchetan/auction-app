package com.deepintent.auction.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private String address;
    private BigDecimal price;
}
