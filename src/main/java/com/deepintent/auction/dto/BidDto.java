package com.deepintent.auction.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {

    private String id;
    private String auctionId;
    private String bidderId;
    private BigDecimal amount;

}
