package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Auction;
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

import static com.deepintent.auction.domain.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AuctionRepositoryTest {

    private Auction auction;
    private Product product;

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
        assertEquals(product, auction.getProduct());
        assertEquals(CREATED, auction.getStatus());
        assertEquals(BigDecimal.valueOf(5000.00), auction.getStartingPrice());
    }

    @Test
    public void shouldReturnAllAuctions() {
        List<Auction> auctions = auctionRepository.findAll();

        assertNotNull(auctions);
        assertEquals(1, auctions.size());
    }


    @Test
    public void shouldUpdateAuction() {
        auction.setStartingPrice(BigDecimal.valueOf(4000.00));

        Auction updatedAuction = auctionRepository.save(auction);

        assertEquals(BigDecimal.valueOf(4000.00), updatedAuction.getStartingPrice());
    }

    @Test
    public void shouldDeleteAuction() {
        auctionRepository.deleteById(auction.getId());
        Optional<Auction> deletedAuction = auctionRepository.findById(auction.getId());

        assertFalse(deletedAuction.isPresent());
    }

    private Auction persistAuction() {
        Auction auction = new Auction();
        auction.setProduct(getProduct());
        auction.setStartTime(LocalDate.now());
        auction.setEndTime(LocalDate.now().plusDays(1));
        auction.setStartingPrice(BigDecimal.valueOf(5000.00));
        auction.setStatus(CREATED);
        return auctionRepository.save(auction);
    }

    public  Product getProduct() {
        product = TestData.createProduct(productRepository);
        return product;
    }
}
