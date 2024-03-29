package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Bidder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
        bid = persistBid(BigDecimal.valueOf(3000.00));
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
        assertEquals(bidder.getId(), bid.getBidderId());
        assertEquals(BigDecimal.valueOf(3000.00), bid.getAmount());
    }

    @Test
    public void shouldReturnAllBids() {
        List<Bid> bids = bidRepository.findAll();

        assertNotNull(bids);
        assertEquals(1, bids.size());
    }

    @Test
    public void shouldReturnAllBidsForBidder() {
        persistBid(BigDecimal.valueOf(3000.00));
        List<Bid> bids = bidRepository.findByBidderId(bidder.getId());

        assertEquals(2, bids.size());
    }

    @Test
    public void shouldReturnAllBidsForAuction() {
        persistBid(BigDecimal.valueOf(3000.00));
        List<Bid> bids = bidRepository.findByAuctionId(auction.getId());

        assertEquals(2, bids.size());
    }

    @Test
    public void shouldReturnAllBidsForUserAndAuction() {
        persistBid(BigDecimal.valueOf(3000.00));
        List<Bid> bids = bidRepository.findByAuctionIdAndBidderId(auction.getId(), bidder.getId());

        assertEquals(2, bids.size());
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

    @Test
    public void shouldReturnHighestBidAmount() {
        persistBid(BigDecimal.valueOf(4000.00));
        persistBid(BigDecimal.valueOf(3000.00));

        Bid bid = bidRepository.findFirstByAuctionIdOrderByAmountDesc("id");

        assertNotNull(bid);
        assertEquals(BigDecimal.valueOf(4000.00), bid.getAmount());
    }

    private Bid persistBid(BigDecimal amount) {
        auction = TestData.createAuction(productRepository, auctionRepository);
        bidder = TestData.createBidder(bidderRepository);
        Bid bid = Bid.builder()
                .auctionId(auction.getId())
                .bidderId(bidder.getId())
                .date("2018-09-16")
                .amount(amount)
                .build();
        return bidRepository.save(bid);
    }

}
