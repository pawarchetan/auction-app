package com.deepintent.auction.graphql;

import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.BidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BidResolverTest {

    @InjectMocks
    private BidResolver bidResolver;

    @Mock
    private BidService bidService;

    @Test
    public void shouldResolveAndCallCreateBidService() {
        BidDto bidDto = TestData.getBidDto();
        Bid dummyBid = TestData.createDummyBid();
        when(bidService.createBid(any())).thenReturn(dummyBid);

        Bid bid = bidResolver.createBid(bidDto);

        assertNotNull(bid);
        assertEquals(dummyBid, bid);
    }

    @Test
    public void shouldResolveAndCallGetAllBidService() {
        List<Bid> bids = TestData.getAllBids();
        when(bidService.getAllBids()).thenReturn(bids);

        List<Bid> bidList = bidResolver.getAllBids();

        assertNotNull(bidList);
        assertEquals(1, bidList.size());
    }

    @Test
    public void shouldResolveAndCallUpdateBidService() {
        BidDto bidDto = TestData.getBidDto();
        Bid dummyBid = TestData.createDummyBid();
        when(bidService.updateBid(any())).thenReturn(dummyBid);

        Bid bid = bidResolver.updateBid(bidDto);

        assertNotNull(bid);
        assertEquals(dummyBid, bid);
    }

    @Test
    public void shouldResolveAndCallDeleteBidService() {
        when(bidService.deleteBid("id")).thenReturn(true);
        Boolean isDeleted = bidResolver.deleteBid("id");

        assertTrue(isDeleted);
    }

}