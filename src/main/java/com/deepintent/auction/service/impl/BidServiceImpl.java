package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.dto.BidDto;
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

        validator.validateIsAuctionFinished(auction); //validate already finished auction

        Bid highestAmountBid = bidRepository.findFirstByAuctionIdOrderByAmountDesc(bidDto.getAuctionId());

        if (validator.validateIsTargetAuctionPriceMet(bidDto, auction)) {   //validate if target price met by your bid
            if (highestAmountBid != null) {
                bidDto.setAmount(highestAmountBid.getAmount().add(BigDecimal.valueOf(0.01)));
            }
            return createBidAndAuctionWithStatusFinished(bidDto, auction);
        }

        validator.validateIfHigherAmountBidExist(highestAmountBid, bidDto);

        updateAuctionStatus(auction, Status.ACTIVE);

        Bid bid = mapBidDtoToBid(bidDto);
        return bidRepository.save(bid);
    }

    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
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
        validator.validateIsBidExist(id);

        bidRepository.deleteById(id);
        return true;
    }

    private Bid createBidAndAuctionWithStatusFinished(BidDto bidDto, Auction auction) {
        Bid bidToAttach = mapBidDtoToBid(bidDto);
        Bid bid = bidRepository.save(bidToAttach);
        updateAuctionStatus(auction, Status.FINISHED);
        return bid;
    }

    private Bid mapBidDtoToBid(BidDto bidDto) {
        return Bid.builder()
                .auctionId(bidDto.getAuctionId())
                .bidderId(bidDto.getBidderId())
                .amount(bidDto.getAmount())
                .build();
    }

    private void updateAuctionStatus(Auction auction, Status status) {
        AuctionDto auctionDto = AuctionDto.builder()
                .id(auction.getId())
                .status(status)
                .build();
        auctionService.updateAuction(auctionDto);
    }

}
