package com.deepintent.auction.service;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;

import java.util.List;

public interface BidderService {

    Bidder createBidder(BidderDto bidderDto);

    List<Bidder> getAllBidders();

    Bidder getBidderById(String id);

    Bidder updateBidder(BidderDto bidderDto);

    Boolean deleteBidder(String id);

}
