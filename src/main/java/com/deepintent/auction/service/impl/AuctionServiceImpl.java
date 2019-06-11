package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;
    private ProductRepository productRepository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository, ProductRepository productRepository) {
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Auction createAuction(AuctionDto auctionDto) {
        Optional<Product> product = productRepository.findById(auctionDto.getProductId());
        if (product.isPresent()) {
            Auction auction = Auction.builder()
                    .productId(product.get().getId())
                    .targetPrice(auctionDto.getTargetPrice())
                    .status(auctionDto.getStatus())
                    .reservePrice(auctionDto.getReservePrice())
                    .build();
            return auctionRepository.save(auction);
        }
        throw new EntityNotFoundException("Product not found for given id : " + auctionDto.getProductId());
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(String id) {
        Optional<Auction> auction = auctionRepository.findById(id);
        return auction.orElse(null);
    }

    @Override
    public Auction updateAuction(AuctionDto auctionDto) {
        Optional<Product> product = productRepository.findById(auctionDto.getProductId());
        if (product.isPresent()) {
            Auction auction = Auction.builder()
                    .id(auctionDto.getId())
                    .productId(product.get().getId())
                    .targetPrice(auctionDto.getTargetPrice())
                    .status(auctionDto.getStatus())
                    .reservePrice(auctionDto.getReservePrice())
                    .build();
            return auctionRepository.save(auction);
        }
        throw new EntityNotFoundException("Product not found for given id : " + auctionDto.getProductId());
    }

    @Override
    public Boolean deleteAuction(String id) {
        auctionRepository.deleteById(id);
        return true;
    }

}
