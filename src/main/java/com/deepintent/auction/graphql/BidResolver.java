package com.deepintent.auction.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.deepintent.auction.domain.Bid;
import com.deepintent.auction.dto.BidDto;
import com.deepintent.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BidResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private BidService bidService;

    @Autowired
    public BidResolver(BidService bidService) {
        this.bidService = bidService;
    }

    public Bid createBid(BidDto bidDto) {
        return bidService.createBid(bidDto);
    }

    public List<Bid> getAllBids() {
        return bidService.getAllBids();
    }

    public List<Bid> getAllBidsForAuction(String id) {
        return bidService.getAllBidsForAuction(id);
    }

    public List<Bid> getAllBidsForBidder(String id) {
        return bidService.getAllBidsForBidder(id);
    }

    public List<Bid> getAllBidsForBidderAndAuction(String bidderId, String auctionId) {
        return bidService.getAllBidsForBidderAndAuction(bidderId, auctionId);
    }

    public Bid updateBid(BidDto bidDto) {
        return bidService.updateBid(bidDto);
    }

    public Boolean deleteBid(String id) {
        return bidService.deleteBid(id);
    }

}
