package com.deepintent.auction.service;

import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.dto.BidDto;

import java.util.List;

public interface BidService {

    Bid createBid(BidDto bidDto);

    List<Bid> getAllBids();

    Bid updateBid(BidDto bidDto);

    Boolean deleteBid(String id);

}
