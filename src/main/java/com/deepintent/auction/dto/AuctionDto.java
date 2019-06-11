package com.deepintent.auction.dto;

import com.deepintent.auction.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDto {

    private String id;
    private String productId;
    private Status status;
    private BigDecimal targetPrice;
    private BigDecimal reservePrice;

}
