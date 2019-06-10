package com.deepintent.auction.graphql;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.BidderService;
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
public class BidderResolverTest {

    @InjectMocks
    private BidderResolver bidderResolver;

    @Mock
    private BidderService bidderService;

    @Test
    public void shouldResolveAndCallCreateBidderService() {
        BidderDto bidderDto = TestData.getBidderDto();
        Bidder dummyBidder = TestData.createDummyBidder();
        when(bidderService.createBidder(any())).thenReturn(dummyBidder);

        Bidder bidder = bidderResolver.createBidder(bidderDto);

        assertNotNull(bidder);
        assertEquals(dummyBidder, bidder);
    }

    @Test
    public void shouldResolveAndCallGetAllBidderService() {
        List<Bidder> bidders = TestData.getAllBidders();
        when(bidderService.getAllBidders()).thenReturn(bidders);

        List<Bidder> bidderList = bidderResolver.getAllBidders();

        assertNotNull(bidderList);
        assertEquals(1, bidderList.size());
    }

    @Test
    public void shouldResolveAndCallUpdateBidderService() {
        BidderDto bidderDto = TestData.getBidderDto();
        Bidder dummyBidder = TestData.createDummyBidder();
        when(bidderService.updateBidder(any())).thenReturn(dummyBidder);

        Bidder bidder = bidderResolver.updateBidder(bidderDto);

        assertNotNull(bidder);
        assertEquals(dummyBidder, bidder);
    }

    @Test
    public void shouldResolveAndCallDeleteBidderService() {
        when(bidderService.deleteBidder("id")).thenReturn(true);
        Boolean isDeleted = bidderResolver.deleteBidder("id");

        assertTrue(isDeleted);
    }
}
