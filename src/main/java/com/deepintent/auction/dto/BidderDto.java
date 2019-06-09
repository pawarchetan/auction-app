package com.deepintent.auction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BidderDto {

    private String id;
    private String firstName;
    private String lastName;

}
