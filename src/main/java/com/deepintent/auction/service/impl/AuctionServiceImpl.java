package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.service.AuctionService;
import com.deepintent.auction.util.DtoMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction createAuction(AuctionDto auctionDto) {
        //TODO validation of product details
        Product product = DtoMaper.build(auctionDto.getProduct());
        Auction auction = Auction.builder()
                .product(product)
                .startTime(auctionDto.getStartTime())
                .endTime(auctionDto.getEndTime())
                .startingPrice(auctionDto.getStartingPrice())
                .status(auctionDto.getStatus())
                .build();
        return auctionRepository.save(auction);
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction updateAuction(AuctionDto auctionDto) {
        Product product = DtoMaper.build(auctionDto.getProduct());
        Auction auction = Auction.builder()
                .product(product)
                .startTime(auctionDto.getStartTime())
                .endTime(auctionDto.getEndTime())
                .startingPrice(auctionDto.getStartingPrice())
                .status(auctionDto.getStatus())
                .build();
        return auctionRepository.save(auction);
    }

    @Override
    public void deleteAuction(String id) {
        auctionRepository.deleteById(id);
    }

}
