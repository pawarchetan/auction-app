package com.deepintent.auction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Document
public class Auction {

    @Id
    private String id;
    private Product product;
    private LocalDate startTime;
    private LocalDate endTime;
    private Status status;
    private BigDecimal startingPrice;

}
