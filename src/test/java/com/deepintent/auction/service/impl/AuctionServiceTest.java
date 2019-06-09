package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuctionServiceTest {

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Mock
    private AuctionRepository auctionRepository;


    @Test
    public void shouldCreateAuction() {
        AuctionDto auctionDto = TestData.getAuctionDto();
        Auction dummyAuction = TestData.createDummyAuction();
        when(auctionRepository.save(any())).thenReturn(dummyAuction);

        Auction auction = auctionService.createAuction(auctionDto);

        assertNotNull(auction);
        assertEquals(dummyAuction, auction);
    }

    @Test
    public void shouldReturnAllAuctions() {
        List<Auction> auctions = TestData.getAllAuctions();
        when(auctionRepository.findAll()).thenReturn(auctions);

        List<Auction> auctionList = auctionService.getAllAuctions();

        assertNotNull(auctionList);
        assertEquals(1, auctionList.size());
    }

    @Test
    public void shouldUpdateAuction() {
        AuctionDto auctionDto = TestData.getAuctionDto();
        Auction dummyAuction = TestData.createDummyAuction();
        when(auctionRepository.save(any())).thenReturn(dummyAuction);

        Auction auction = auctionService.updateAuction(auctionDto);

        assertNotNull(auction);
        assertEquals(dummyAuction, auction);
    }

    @Test
    public void shouldDeleteAuctionById() {
        doNothing().when(auctionRepository).deleteById("id");
        auctionService.deleteAuction("id");
    }

}