package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {

    List<Bid> findByAuctionId(String auctionId);

    List<Bid> findByBidderId(String userId);

    List<Bid> findByAuctionIdAndBidderId(String auctionId, String userId);

    Bid findFirstByAuctionIdOrderByAmountDesc(String auctionId);

}
