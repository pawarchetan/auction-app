package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.repository.BidderRepository;
import com.deepintent.auction.repository.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BidderServiceTest {

    @InjectMocks
    private BidderServiceImpl bidderService;

    @Mock
    private BidderRepository bidderRepository;

    private BidderDto bidderDto;
    private Bidder dummyBidder;

    @Before
    public void setUp() {
        bidderDto = TestData.getBidderDto();
        dummyBidder = TestData.createDummyBidder();
    }

    @Test
    public void shouldCreateBidder() {
        when(bidderRepository.save(any())).thenReturn(dummyBidder);

        Bidder bidder = bidderService.createBidder(bidderDto);

        assertNotNull(bidder);
        assertEquals(dummyBidder, bidder);
    }

    @Test
    public void shouldReturnAllBidders() {
        List<Bidder> bidders = TestData.getAllBidders();
        when(bidderRepository.findAll()).thenReturn(bidders);

        List<Bidder> bidderList = bidderService.getAllBidders();

        assertNotNull(bidderList);
        assertEquals(1, bidderList.size());
    }

    @Test
    public void shouldReturnBidderById() {
        when(bidderRepository.findById("id")).thenReturn(Optional.ofNullable(dummyBidder));
        Bidder bidder = bidderService.getBidderById("id");

        assertNotNull(bidder);
        assertEquals("id", bidder.getId());
    }

    @Test
    public void shouldUpdateBidder() {
        BidderDto bidderDto = TestData.getBidderDto();
        Bidder dummyBidder = TestData.createDummyBidder();
        when(bidderRepository.findById("id")).thenReturn(Optional.ofNullable(dummyBidder));
        when(bidderRepository.save(any())).thenReturn(dummyBidder);

        Bidder bidder = bidderService.updateBidder(bidderDto);

        assertNotNull(bidder);
        assertEquals(dummyBidder, bidder);
    }

    @Test
    public void shouldDeleteProductById() {
        when(bidderRepository.findById("id")).thenReturn(Optional.ofNullable(dummyBidder));
        doNothing().when(bidderRepository).deleteById("id");
        Boolean isDeleted = bidderService.deleteBidder("id");

        assertTrue(isDeleted);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfBidderNotExistWhileUpdatingBidder() {
        when(bidderRepository.findById("id")).thenReturn(Optional.empty());
        bidderService.updateBidder(bidderDto);
    }

}