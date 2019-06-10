package com.deepintent.auction.exception;

public class EntityAlreadyExistException extends IllegalArgumentException {
    public EntityAlreadyExistException(String s) {
        super(s);
    }
}
