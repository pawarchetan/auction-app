package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class BidRepositoryTest {

    private Bid bid;
    private Auction auction;
    private Bidder bidder;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidderRepository bidderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        bid = persistBid();
    }

    @After
    public void tearDown() {
        bidRepository.deleteAll();
        bidderRepository.deleteAll();
        productRepository.deleteAll();
        auctionRepository.deleteAll();
    }

    @Test
    public void shouldCreateBidAgainstAuction() {
        assertNotNull(bid);
        assertNotNull(bid.getId());
        assertEquals(auction.getId(), bid.getAuctionId());
        assertEquals(bidder.getId(), bid.getUserId());
        assertEquals(BigDecimal.valueOf(3000.00), bid.getAmount());
    }

    @Test
    public void shouldReturnAllBids() {
        List<Bid> bids = bidRepository.findAll();

        assertNotNull(bids);
        assertEquals(1, bids.size());
    }

    @Test
    public void shouldUpdateBid() {
        bid.setId(bid.getId());
        bid.setAmount(BigDecimal.valueOf(45000.00));

        Bid updatedBid= bidRepository.save(bid);

        assertEquals(BigDecimal.valueOf(45000.00), updatedBid.getAmount());
    }

    @Test
    public void shouldDeleteBidForAGivenId() {
        bidRepository.deleteById(bid.getId());
        Optional<Bid> deletedBid = bidRepository.findById(bid.getId());

        assertFalse(deletedBid.isPresent());
    }

    private Bid persistBid() {
        auction = TestData.createAuction(productRepository, auctionRepository);
        bidder = TestData.createBidder(bidderRepository);
        Bid bid = new Bid();
        bid.setAuctionId(auction.getId());
        bid.setUserId(bidder.getId());
        bid.setTime(LocalDate.now());
        bid.setAmount(BigDecimal.valueOf(3000.00));
        return bidRepository.save(bid);
    }

}
