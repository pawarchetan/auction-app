package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.BidStatus;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.InvalidAmountException;
import com.deepintent.auction.exception.InvalidAuctionAccessException;
import com.deepintent.auction.repository.BidRepository;
import com.deepintent.auction.service.AuctionService;
import com.deepintent.auction.service.BidService;
import com.deepintent.auction.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private BidRepository bidRepository;
    private AuctionService auctionService;
    private Validator validator;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, AuctionService auctionService, Validator validator) {
        this.bidRepository = bidRepository;
        this.auctionService = auctionService;
        this.validator = validator;
    }

    @Override
    public Bid createBid(BidDto bidDto) {
        validator.validateIsBidderExist(bidDto.getBidderId()); //validate bidder

        Auction auction = validator.validateIsAuctionExist(bidDto.getAuctionId()); //validate auction

        validateIfAuctionAlreadyFinished(auction);

        Bid highestAmountBid = bidRepository.findFirstByAuctionIdOrderByAmountDesc(bidDto.getAuctionId()); //TODO distributed cache

        Bid isFinalBid = validateIfAuctionTargetPriceMet(highestAmountBid, bidDto, auction);
        if (isFinalBid != null) {
            return isFinalBid;
        }

        validateHigherAmountBid(highestAmountBid, bidDto);
        updateAuctionStatus(auction, Status.ACTIVE);

        Bid bid = mapBidDtoToBid(bidDto, false);
        return bidRepository.save(bid);
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    @Override
    public List<Bid> getAllBidsForBidder(String bidderId) {
        return bidRepository.findByBidderId(bidderId);
    }

    @Override
    public List<Bid> getAllBidsForAuction(String auctionId) {
        return bidRepository.findByAuctionId(auctionId);
    }

    @Override
    public List<Bid> getAllBidsForBidderAndAuction(String bidderId, String auctionId) {
        return bidRepository.findByAuctionIdAndBidderId(bidderId, auctionId);
    }

    @Override
    public Bid updateBid(BidDto bidDto) {
        Bid bid = Bid.builder()
                .id(bidDto.getId())
                .auctionId(bidDto.getAuctionId())
                .bidderId(bidDto.getBidderId())
                .amount(bidDto.getAmount())
                .build();
        return bidRepository.save(bid);
    }

    @Override
    public Boolean deleteBid(String id) {
        bidRepository.deleteById(id);
        return true;
    }

    private Bid createBidAndAuctionWithStatusFinished(BidDto bidDto, Auction auction) {
        Bid bidToAttach = mapBidDtoToBid(bidDto, true);
        Bid bid = bidRepository.save(bidToAttach);
        updateAuctionStatus(auction, Status.FINISHED);
        return bid;
    }

    private Bid mapBidDtoToBid(BidDto bidDto, boolean isFinalBid) {
        BidStatus response = isFinalBid ? BidStatus.FINAL_BID : BidStatus.BID_PLACED;
        return Bid.builder()
                .auctionId(bidDto.getAuctionId())
                .bidderId(bidDto.getBidderId())
                .amount(bidDto.getAmount())
                .bidStatus(response)
                .build();
    }

    private void updateAuctionStatus(Auction auction, Status status) {
        AuctionDto auctionDto = AuctionDto.builder()
                .id(auction.getId())
                .productId(auction.getProductId())
                .targetPrice(auction.getTargetPrice())
                .reservePrice(auction.getReservePrice())
                .status(status)
                .build();
        auctionService.updateAuction(auctionDto);
    }

    private void validateIfAuctionAlreadyFinished(Auction auction) {
        if (validator.validateIsAuctionFinished(auction)) {
            throw new InvalidAuctionAccessException("Auction with id :" + auction.getId() + " already closed");
        }
    }

    private Bid validateIfAuctionTargetPriceMet(Bid highestAmountBid, BidDto bidDto, Auction auction) {
        if (validator.validateIsTargetAuctionPriceMet(bidDto, auction)) {
            if (highestAmountBid != null && highestAmountBid.getAmount().compareTo(auction.getReservePrice()) >= 0) {
                bidDto.setAmount(highestAmountBid.getAmount().add(BigDecimal.valueOf(0.01)));  //higher price should win at second price
                // auction
            }
            return createBidAndAuctionWithStatusFinished(bidDto, auction);
        }
        return null;
    }

    private void validateHigherAmountBid(Bid highestAmountBid, BidDto bidDto) {
        if (validator.validateIfHigherAmountBidExist(highestAmountBid, bidDto)) {
            throw new InvalidAmountException("Higher bid amount already exist, please bid high amount");
        }
    }

}
