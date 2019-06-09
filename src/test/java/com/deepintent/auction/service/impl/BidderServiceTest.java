package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.repository.BidderRepository;
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
public class BidderServiceTest {

    @InjectMocks
    private BidderServiceImpl bidderService;

    @Mock
    private BidderRepository bidderRepository;

    @Test
    public void shouldCreateBidder() {
        BidderDto bidderDto = TestData.getBidderDto();
        Bidder dummyBidder = TestData.createDummyBidder();
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
    public void shouldUpdateBidder() {
        BidderDto bidderDto = TestData.getBidderDto();
        Bidder dummyBidder = TestData.createDummyBidder();
        when(bidderRepository.save(any())).thenReturn(dummyBidder);

        Bidder bidder = bidderService.updateBidder(bidderDto);

        assertNotNull(bidder);
        assertEquals(dummyBidder, bidder);

    }

    @Test
    public void shouldDeleteProductById() {
        doNothing().when(bidderRepository).deleteById("id");
        bidderService.deleteBidder("test-id");
    }

}