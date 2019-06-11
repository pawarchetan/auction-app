package com.deepintent.auction.service.impl;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.BidStatus;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.InvalidAmountException;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.BidRepository;
import com.deepintent.auction.repository.BidderRepository;
import com.deepintent.auction.repository.TestData;
import com.deepintent.auction.service.AuctionService;
import com.deepintent.auction.util.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BidServiceTest {

    private BidDto bidDto;
    private Bid dummyBid;
    private Bid finishedBid;
    private Auction dummyAuction;
    private AuctionDto auctionDto;

    @InjectMocks
    private BidServiceImpl bidService;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidderRepository bidderRepository;

    @Mock
    private AuctionService auctionService;

    @Mock
    private Validator validator;

    @Before
    public void setUp() {
        bidDto = TestData.getBidDto();
        dummyBid = TestData.createDummyBid();
        finishedBid = TestData.createDummyBid();
        dummyAuction = TestData.createDummyAuction();
        auctionDto = TestData.getAuctionDto();
    }

    @Test
    public void shouldCreateABid() {
        when(validator.validateIsTargetAuctionPriceMet(bidDto, dummyAuction)).thenReturn(false);
        when(validator.validateIsAuctionExist(any())).thenReturn(dummyAuction);
        when(bidRepository.save(any())).thenReturn(dummyBid);
        Bid createdBid = bidService.createBid(bidDto);

        assertNotNull(createdBid);
        assertEquals(createdBid.getAmount(), BigDecimal.valueOf(2000.00));
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldThrowAnExceptionIfHigherAmountBidExist() {
        when(validator.validateIsTargetAuctionPriceMet(bidDto, dummyAuction)).thenReturn(false);
        when(bidRepository.findFirstByAuctionIdOrderByAmountDesc(bidDto.getAuctionId())).thenReturn(dummyBid);
        when(validator.validateIsAuctionExist(any())).thenReturn(dummyAuction);
        when(validator.validateIfHigherAmountBidExist(dummyBid, bidDto)).thenReturn(true);

        bidService.createBid(bidDto);
    }

    @Test
    public void shouldReturnAuctionWithStatusFinishedOnFirstAchievableBidWithTargetAmount() {
        finishedBid.setBidStatus(BidStatus.FINAL_BID);
        when(auctionService.updateAuction(auctionDto)).thenReturn(dummyAuction);
        when(bidRepository.save(any())).thenReturn(finishedBid);
        when(validator.validateIsTargetAuctionPriceMet(bidDto, dummyAuction)).thenReturn(true);
        when(validator.validateIsAuctionExist(bidDto.getId())).thenReturn(dummyAuction);

        Bid createdBid = bidService.createBid(bidDto);

        assertEquals(BidStatus.FINAL_BID, createdBid.getBidStatus());
    }

    @Test
    public void shouldReturnAllBids() {
        List<Bid> bids = TestData.getAllBids();
        when(bidRepository.findAll()).thenReturn(bids);

        List<Bid> bidList = bidService.getAllBids();

        assertNotNull(bidList);
        assertEquals(1, bidList.size());
    }

    @Test
    public void shouldReturnAllBidsForAnBidder() {
        List<Bid> bids = TestData.getAllBids();
        when(bidRepository.findByBidderId("id")).thenReturn(bids);

        List<Bid> bidList = bidService.getAllBidsForBidder("id");

        assertNotNull(bidList);
        assertEquals(1, bidList.size());
    }

    @Test
    public void shouldReturnAllBidsForAnAuction() {
        List<Bid> bids = TestData.getAllBids();
        when(bidRepository.findByAuctionId("id")).thenReturn(bids);

        List<Bid> bidList = bidService.getAllBidsForAuction("id");

        assertNotNull(bidList);
        assertEquals(1, bidList.size());
    }

    @Test
    public void shouldReturnAllBidsForAnAuctionAndBidder() {
        List<Bid> bids = TestData.getAllBids();
        when(bidRepository.findByAuctionIdAndBidderId("id", "id")).thenReturn(bids);

        List<Bid> bidList = bidService.getAllBidsForBidderAndAuction("id", "id");

        assertNotNull(bidList);
        assertEquals(1, bidList.size());
    }

    @Test
    public void shouldUpdateBidWithDetails() {
        when(bidRepository.findById("id")).thenReturn(java.util.Optional.ofNullable(TestData.createDummyBid()));
        when(bidRepository.save(any())).thenReturn(dummyBid);

        Bid bid = bidService.updateBid(bidDto);

        assertNotNull(bid);
        assertEquals(dummyBid, bid);
    }

    @Test
    public void shouldDeleteBidById() {
        when(bidRepository.findById("id")).thenReturn(java.util.Optional.ofNullable(dummyBid));
        doNothing().when(bidderRepository).deleteById("id");
        Boolean isDeleted = bidService.deleteBid("id");

        assertTrue(isDeleted);
    }

}
