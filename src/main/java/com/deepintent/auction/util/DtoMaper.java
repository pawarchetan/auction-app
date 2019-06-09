package com.deepintent.auction.util;

import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.ProductDto;

public class DtoMaper {

    public static Product build(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .address(productDto.getAddress())
                .price(productDto.getPrice())
                .build();
    }
}
