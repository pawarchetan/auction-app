package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Auction;
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

import static com.deepintent.auction.domain.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AuctionRepositoryTest {

    private Auction auction;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        auction = persistAuction();
    }

    @After
    public void tearDown() {
        auctionRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void shouldCreateAuction() {
        assertNotNull(auction);
        assertNotNull(auction.getId());
        assertNotNull(auction.getProductId());
        assertEquals(CREATED, auction.getStatus());
        assertEquals(BigDecimal.valueOf(5000.00), auction.getTargetPrice());
    }

    @Test
    public void shouldReturnAllAuctions() {
        List<Auction> auctions = auctionRepository.findAll();

        assertNotNull(auctions);
        assertEquals(1, auctions.size());
    }


    @Test
    public void shouldUpdateAuction() {
        auction.setTargetPrice(BigDecimal.valueOf(4000.00));

        Auction updatedAuction = auctionRepository.save(auction);

        assertEquals(BigDecimal.valueOf(4000.00), updatedAuction.getTargetPrice());
    }

    @Test
    public void shouldDeleteAuction() {
        auctionRepository.deleteById(auction.getId());
        Optional<Auction> deletedAuction = auctionRepository.findById(auction.getId());

        assertFalse(deletedAuction.isPresent());
    }

    private Auction persistAuction() {
        Auction auction = TestData.createAuction(productRepository, auctionRepository);
        return auctionRepository.save(auction);
    }

}
