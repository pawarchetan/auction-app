package com.deepintent.auction.util;

import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.domain.Status;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.exception.EntityNotFoundException;
import com.deepintent.auction.exception.InvalidAmountException;
import com.deepintent.auction.exception.InvalidAuctionAccessException;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.BidRepository;
import com.deepintent.auction.repository.BidderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ValidatorTest {

    @InjectMocks
    private Validator validator;

    @Mock
    private BidderRepository bidderRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfBidderDoesNotExist() {
        when(bidderRepository.findById("id")).thenReturn(Optional.empty());
        validator.validateIsBidderExist("id");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfBidDoesNotExist() {
        when(bidRepository.findById("id")).thenReturn(Optional.empty());
        validator.validateIsBidExist("id");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowAnExceptionIfAuctionDoesNotExist() {
        when(auctionRepository.findById("id")).thenReturn(Optional.empty());
        validator.validateIsAuctionExist("id");
    }

    @Test(expected = InvalidAuctionAccessException.class)
    public void shouldThrowAnExceptionIfAuctionIsFinished() {
        Auction auction = Auction.builder()
                .status(Status.FINISHED)
                .build();
        validator.validateIsAuctionFinished(auction);
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

    @Test(expected = InvalidAmountException.class)
    public void shouldThrowAnExceptionIfHigherAmountBidAlreadyExist() {
        BidDto bidDto = BidDto.builder()
                .amount(BigDecimal.valueOf(5000.00))
                .build();
        Bid highestAmountBid = Bid.builder()
                .amount(BigDecimal.valueOf(6000.00))
                .build();

        validator.validateIfHigherAmountBidExist(highestAmountBid, bidDto);
    }

}
