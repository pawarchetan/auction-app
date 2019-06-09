package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.domain.Product;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.dto.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.deepintent.auction.domain.Status.CREATED;

public class TestData {

    public static Product createProduct(ProductRepository productRepository) {
        Product product = getProduct();
        return productRepository.save(product);
    }

    public static Product getProduct() {
        return Product.builder()
                .name("M Docklands")
                .description("House")
                .address("677 LaTrobe St., Docklands - 3008")
                .price(BigDecimal.valueOf(350000.00))
                .build();
    }

    public static Bidder createBidder(BidderRepository bidderRepository) {
        Bidder bidder = createDummyBidder();
        return bidderRepository.save(bidder);
    }

    public static Auction createAuction(ProductRepository productRepository, AuctionRepository auctionRepository) {
        Auction auction = new Auction();
        Product product = createProduct(productRepository);
        auction.setProduct(product);
        auction.setStartTime(LocalDate.now());
        auction.setEndTime(LocalDate.now().plusDays(1));
        auction.setStartingPrice(BigDecimal.valueOf(5000.00));
        auction.setStatus(CREATED);
        return auctionRepository.save(auction);
    }

    public static ProductDto getProductDto() {
        return ProductDto.builder()
                .id("test-id")
                .name("House")
                .price(BigDecimal.valueOf(200.00))
                .build();
    }

    public static BidderDto getBidderDto() {
        return BidderDto.builder()
                .id("id")
                .firstName("first_name")
                .lastName("Last_name")
                .build();
    }

    public static Product createDummyProduct() {
        return Product.builder()
                .id("test-id")
                .name("House")
                .price(BigDecimal.valueOf(200.00))
                .build();
    }

    public static Bidder createDummyBidder() {
        return Bidder.builder()
                .id("id")
                .firstName("first_name")
                .lastName("last_name")
                .build();
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(getProduct());
        return products;
    }

    public static List<Bidder> getAllBidders() {
        List<Bidder> bidders = new ArrayList<>();
        bidders.add(createDummyBidder());
        return bidders;
    }
}
