package com.deepintent.auction.graphql;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.AuctionService;
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
public class AuctionResolverTest {

    @InjectMocks
    private AuctionResolver auctionResolver;

    @Mock
    private AuctionService auctionService;

    @Test
    public void shouldResolveAndCallCreateAuctionService() {
        AuctionDto auctionDto = TestData.getAuctionDto();
        Auction dummyAuction = TestData.createDummyAuction();
        when(auctionService.createAuction(any())).thenReturn(dummyAuction);

        Auction auction = auctionResolver.createAuction(auctionDto);

        assertNotNull(auction);
        assertEquals(dummyAuction, auction);
    }

    @Test
    public void shouldResolveAndCallGetAllAuctionService() {
        List<Auction> auctions = TestData.getAllAuctions();
        when(auctionService.getAllAuctions()).thenReturn(auctions);

        List<Auction> auctionList = auctionResolver.getAllAuctions();

        assertNotNull(auctionList);
        assertEquals(1, auctionList.size());
    }

    @Test
    public void shouldResolveAndCallUpdateAuctionService() {
        AuctionDto auctionDto = TestData.getAuctionDto();
        Auction dummyAuction = TestData.createDummyAuction();
        when(auctionService.updateAuction(any())).thenReturn(dummyAuction);

        Auction auction = auctionResolver.updateAuction(auctionDto);

        assertNotNull(auction);
        assertEquals(dummyAuction, auction);
    }

    @Test
    public void shouldResolveAndCallDeleteAuctionService() {
        when(auctionService.deleteAuction("id")).thenReturn(true);
        Boolean isDeleted = auctionResolver.deleteAuction("id");

        assertTrue(isDeleted);
    }
}
