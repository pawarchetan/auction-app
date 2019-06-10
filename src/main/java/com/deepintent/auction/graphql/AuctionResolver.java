package com.deepintent.auction.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.deepintent.auction.domain.Auction;
import com.deepintent.auction.dto.AuctionDto;
import com.deepintent.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuctionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private AuctionService auctionService;

    @Autowired
    public AuctionResolver(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    public Auction createAuction(AuctionDto auctionDto) {
        return auctionService.createAuction(auctionDto);
    }

    public List<Auction> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    public Auction updateAuction(AuctionDto auctionDto) {
        return auctionService.updateAuction(auctionDto);
    }

    public Boolean deleteAuction(String id) {
        return auctionService.deleteAuction(id);
    }

}
