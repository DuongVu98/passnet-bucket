package com.iucse.passnetbucket.domain.exception;

public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(String message) {
        super(message);
    }

    public BucketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
