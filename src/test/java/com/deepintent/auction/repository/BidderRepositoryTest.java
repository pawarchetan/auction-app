package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Bidder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class BidderRepositoryTest {

    @Autowired
    private BidderRepository bidderRepository;

    private Bidder bidder;

    @Before
    public void setUp() {
        bidder = persistBidder();
    }

    @After
    public void tearDown() {
        bidderRepository.deleteAll();
    }

    @Test
    public void shouldCreateBidderWithGivenMetadata() {
        assertNotNull(bidder);
        assertNotNull(bidder.getId());
        assertEquals("first_name", bidder.getFirstName());
        assertEquals("last_name", bidder.getLastName());
    }

    @Test
    public void shouldGetAllBidders() {
        List<Bidder> bidders = bidderRepository.findAll();

        assertNotNull(bidders);
        assertEquals(1, bidders.size());
    }

    @Test
    public void shouldUpdateBidder() {
        bidder.setId(bidder.getId());
        bidder.setFirstName("Chetan12");
        Bidder updatedBidder = bidderRepository.save(bidder);

        assertEquals(bidder.getFirstName(), updatedBidder.getFirstName());
        assertEquals(bidder.getLastName(), updatedBidder.getLastName());
    }

    @Test
    public void shouldDeleteBidder() {
        bidderRepository.deleteById(bidder.getId());
        Optional<Bidder> deletedBidder = bidderRepository.findById(bidder.getId());

        assertFalse(deletedBidder.isPresent());
    }

    private Bidder persistBidder() {
       return TestData.createBidder(bidderRepository);
    }

}
