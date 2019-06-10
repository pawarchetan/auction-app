package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.repository.BidderRepository;
import com.deepintent.auction.service.BidderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidderServiceImpl implements BidderService {

    private BidderRepository bidderRepository;

    @Autowired
    public BidderServiceImpl(BidderRepository bidderRepository) {
        this.bidderRepository = bidderRepository;
    }

    @Override
    public Bidder createBidder(BidderDto bidderDto) {
        Bidder bidder = Bidder.builder()
                .firstName(bidderDto.getFirstName())
                .lastName(bidderDto.getLastName())
                .build();
        return bidderRepository.save(bidder);
    }

    @Override
    public List<Bidder> getAllBidders() {
        return bidderRepository.findAll();
    }

    @Override
    public Bidder updateBidder(BidderDto bidderDto) {
        Bidder bidder = Bidder.builder()
                .id(bidderDto.getId())
                .firstName(bidderDto.getFirstName())
                .lastName(bidderDto.getLastName())
                .build();
        return bidderRepository.save(bidder);
    }

    @Override
    public Boolean deleteBidder(String id) {
        bidderRepository.deleteById(id);
        return true;
    }
}
