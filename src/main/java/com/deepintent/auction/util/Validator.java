package com.deepintent.auction.util;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.exception.InvalidAmountException;
import com.deepintent.auction.exception.InvalidAuctionAccessException;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.BidRepository;
import com.deepintent.auction.repository.BidderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Validator {

    private BidderRepository bidderRepository;
    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;

    public Validator(BidderRepository bidderRepository, AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.bidderRepository = bidderRepository;
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    @Autowired
    public Validator(BidderRepository bidderRepository, AuctionRepository auctionRepository) {
        this.bidderRepository = bidderRepository;
        this.auctionRepository = auctionRepository;
    }

    public void validateIsBidderExist(String bidderId) {
        Optional<Bidder> bidder = bidderRepository.findById(bidderId);
        if (!bidder.isPresent()) {
            throw new EntityNotFoundException("Bidder not found with given id : " + bidderId);
        }
    }

    public void validateIsBidExist(String bidId) {
        Optional<Bid> bid = bidRepository.findById(bidId);
        if (!bid.isPresent()) {
            throw new EntityNotFoundException("Bid not found with given id : " + bidId);
        }
    }

    public Auction validateIsAuctionExist(String auctionId) {
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        if (!auction.isPresent()) {
            throw new EntityNotFoundException("Auction not found with given id : " + auctionId);
        }
        return auction.get();
    }

    public void validateIsAuctionFinished(Auction auction) {
        if (Status.FINISHED == auction.getStatus()) {
            throw new InvalidAuctionAccessException("Auction with id :" + auction.getId() + " already closed");
        }
    }

    public boolean validateIsTargetAuctionPriceMet(BidDto bidDto, Auction auction) {
        return bidDto.getAmount().compareTo(auction.getTargetPrice()) >= 0;
    }

    public void validateIfHigherAmountBidExist(Bid bidWithHighestAmount, BidDto bidDto) {
        if (bidWithHighestAmount.getAmount().compareTo(bidDto.getAmount()) > 0) {
            throw new InvalidAmountException("Higher bid amount already exist, please bid high amount");
        }
    }

}
