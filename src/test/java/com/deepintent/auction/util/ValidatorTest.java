package com.deepintent.auction.util;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.service.AuctionService;
import com.deepintent.auction.service.BidderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ValidatorTest {

    @InjectMocks
    private Validator validator;

    @Mock
    private BidderService bidderService;

    @Mock
    private AuctionService auctionService;

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfBidderDoesNotExist() {
        when(bidderService.getBidderById("id")).thenReturn(null);
        validator.validateIsBidderExist("id");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfAuctionDoesNotExist() {
        when(auctionService.getAuctionById("id")).thenReturn(null);
        validator.validateIsAuctionExist("id");
    }

    public void shouldThrowAnExceptionIfAuctionIsFinished() {
        Auction auction = Auction.builder()
                .status(Status.FINISHED)
                .build();
        assertFalse(validator.validateIsAuctionFinished(auction));
    }

    @Test
    public void shouldReturnTrueIfAuctionPriceIsMet() {
        BidDto bidDto = BidDto.builder()
                .amount(BigDecimal.valueOf(5000.00))
                .build();
        Auction auction = Auction.builder()
                .targetPrice(BigDecimal.valueOf(4500.00))
                .build();

        boolean isPriceMet = validator.validateIsTargetAuctionPriceMet(bidDto, auction);

        assertTrue(isPriceMet);
    }

    @Test
    public void shouldThrowAnExceptionIfHigherAmountBidAlreadyExist() {
        BidDto bidDto = BidDto.builder()
                .amount(BigDecimal.valueOf(5000.00))
                .build();
        Bid highestAmountBid = Bid.builder()
                .amount(BigDecimal.valueOf(6000.00))
                .build();

        assertTrue(validator.validateIfHigherAmountBidExist(highestAmountBid, bidDto));
    }

}
