package com.deepintent.auction.service;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;

import java.util.List;

public interface BidderService {

    Bidder createBidder(BidderDto bidderDto);

    List<Bidder> getAllBidders();

    Bidder updateBidder(BidderDto bidderDto);

    void deleteBidder(String id);
}