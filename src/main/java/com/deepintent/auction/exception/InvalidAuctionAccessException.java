package com.deepintent.auction.exception;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

public class InvalidAuctionAccessException extends InvalidDataAccessResourceUsageException {
    public InvalidAuctionAccessException(String msg) {
        super(msg);
    }
}
