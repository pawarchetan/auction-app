package com.deepintent.auction.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.deepintent.auction.domain.Bidder;
import com.deepintent.auction.dto.BidderDto;
import com.deepintent.auction.service.BidderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BidderResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private BidderService bidderService;

    @Autowired
    public BidderResolver(BidderService bidderService) {
        this.bidderService = bidderService;
    }

    public Bidder createBidder(BidderDto bidderDto) {
        return bidderService.createBidder(bidderDto);
    }

    public List<Bidder> getAllBidders() {
        return bidderService.getAllBidders();
    }

    public Bidder getBidderById(String id) {
        return bidderService.getBidderById(id);
    }

    public Bidder updateBidder(BidderDto bidderDto) {
        return bidderService.updateBidder(bidderDto);
    }

    public Boolean deleteBidder(String id) {
        return bidderService.deleteBidder(id);
    }

}
