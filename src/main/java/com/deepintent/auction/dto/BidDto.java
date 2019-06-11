package com.deepintent.auction.dto;


import com.deepintent.auction.domain.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {

    @Id
    private String id;
    @Indexed
    private String auctionId;
    @Indexed
    private String bidderId;
    private BigDecimal amount;
    private BidStatus bidStatus;

}
