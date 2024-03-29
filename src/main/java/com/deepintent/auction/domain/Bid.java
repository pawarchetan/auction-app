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
public class Bid {

    @Id
    private String id;
    private String auctionId;
    private String bidderId;
    private BigDecimal amount;
    private String date;
    private BidStatus bidStatus;

}
