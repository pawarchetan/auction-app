package com.deepintent.auction.service;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.dto.AuctionDto;

import java.util.List;

public interface AuctionService {

    Auction createAuction(AuctionDto auctionDto);

    List<Auction> getAllAuctions();

    Auction updateAuction(AuctionDto auctionDto);

    Boolean deleteAuction(String id);
}
