package com.deepintent.auction.util;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.service.AuctionService;
import com.deepintent.auction.service.BidderService;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private BidderService bidderService;
    private AuctionService auctionService;

    public Validator(BidderService bidderService, AuctionService auctionService) {
        this.bidderService = bidderService;
        this.auctionService = auctionService;
    }

    public void validateIsBidderExist(String bidderId) {
        Bidder bidder = bidderService.getBidderById(bidderId);
        if (bidder == null) {
            throw new EntityNotFoundException("Bidder not found with given id : " + bidderId);
        }
    }

    public Auction validateIsAuctionExist(String auctionId) {
        Auction auction = auctionService.getAuctionById(auctionId);
        if (auction == null) {
            throw new EntityNotFoundException("Auction not found with given id : " + auctionId);
        }
        return auction;
    }

    public boolean validateIsAuctionFinished(Auction auction) {
        return Status.FINISHED == auction.getStatus();
    }

    public boolean validateIsTargetAuctionPriceMet(BidDto bidDto, Auction auction) {
        return bidDto.getAmount().compareTo(auction.getTargetPrice()) >= 0;
    }

    public boolean validateIfHigherAmountBidExist(Bid bidWithHighestAmount, BidDto bidDto) {
        return bidWithHighestAmount != null
                && bidWithHighestAmount.getAmount().compareTo(bidDto.getAmount()) >= 0;
    }

}
