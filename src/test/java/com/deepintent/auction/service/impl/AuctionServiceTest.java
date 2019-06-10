package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.ProductRepository;
import com.deepintent.auction.repository.TestData;
import org.junit.Before;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuctionServiceTest {

    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private ProductRepository productRepository;

    private AuctionDto auctionDto;
    private Auction dummyAuction;

    @Before
    public void setUp() {
        auctionDto = TestData.getAuctionDto();
        dummyAuction = TestData.createDummyAuction();
    }

    @Test
    public void shouldCreateAuction() {
        when(productRepository.findById("id")).thenReturn(java.util.Optional.ofNullable(TestData.createDummyProduct()));
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
        when(productRepository.findById("id")).thenReturn(java.util.Optional.ofNullable(TestData.createDummyProduct()));
        when(auctionRepository.save(any())).thenReturn(dummyAuction);

        Auction auction = auctionService.updateAuction(auctionDto);

        assertNotNull(auction);
        assertEquals(dummyAuction, auction);
    }

    @Test
    public void shouldDeleteAuctionById() {
        when(auctionRepository.findById("id")).thenReturn(java.util.Optional.ofNullable(dummyAuction));
        doNothing().when(auctionRepository).deleteById("id");
        Boolean isDeleted = auctionService.deleteAuction("id");

        assertTrue(isDeleted);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfInvalidProductIdPassedForAuctionCreation() {
        when(productRepository.findById("id")).thenReturn(java.util.Optional.empty());
        auctionService.createAuction(auctionDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfAuctionDoesNotExistOnDeleteAuction() {
        when(auctionRepository.findById("id")).thenReturn(java.util.Optional.empty());
        auctionService.deleteAuction("id");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfAuctionDoesNotExistOnUpdateAuction() {
        when(auctionRepository.findById("id")).thenReturn(java.util.Optional.empty());
        auctionService.updateAuction(auctionDto);
    }

}