package com.deepintent.auction.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document
public class Bidder {

    @Id
    private String id;
    private String firstName;
    private String lastName;

}
