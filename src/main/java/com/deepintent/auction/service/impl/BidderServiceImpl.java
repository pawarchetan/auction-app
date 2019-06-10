package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.BidderRepository;
import com.deepintent.auction.service.BidderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Bidder> bidder = bidderRepository.findById(bidderDto.getId());
        if (bidder.isPresent()) {
            Bidder bidderToAdd = Bidder.builder()
                    .id(bidderDto.getId())
                    .firstName(bidderDto.getFirstName())
                    .lastName(bidderDto.getLastName())
                    .build();
            return bidderRepository.save(bidderToAdd);
        }
        throw new EntityNotFoundException("Bidder not exist with given id: " + bidderDto.getId());
    }

    @Override
    public Boolean deleteBidder(String id) {
        Optional<Bidder> bidder = bidderRepository.findById(id);
        if (bidder.isPresent()) {
            bidderRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Bidder not exist with given id: " + id);
    }
}
