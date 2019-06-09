package com.deepintent.auction.dto;

import com.deepintent.auction.domain.Status;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class AuctionDto {

    private String id;
    private ProductDto product;
    private LocalDate startTime;
    private LocalDate endTime;
    private Status status;
    private BigDecimal startingPrice;
}
