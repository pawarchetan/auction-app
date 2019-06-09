package com.deepintent.auction.repository;

import com.deepintent.auction.domain.Bidder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidderRepository extends MongoRepository<Bidder, String> {
}
