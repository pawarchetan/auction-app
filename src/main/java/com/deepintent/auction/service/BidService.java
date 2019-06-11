package com.deepintent.auction.service;

import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.dto.BidDto;

import java.util.List;

public interface BidService {

    Bid createBid(BidDto bidDto);

    List<Bid> getAllBids();

    List<Bid> getAllBidsForBidder(String bidderId);

    List<Bid> getAllBidsForAuction(String auctionId);

    List<Bid> getAllBidsForBidderAndAuction(String bidderId, String auctionId);

    Bid updateBid(BidDto bidDto);

    Boolean deleteBid(String id);

}
