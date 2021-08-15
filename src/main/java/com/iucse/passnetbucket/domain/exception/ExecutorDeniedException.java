package com.iucse.passnetbucket.domain.exception;

public class ExecutorDeniedException extends RuntimeException {
    public ExecutorDeniedException(String message) {
        super(message);
    }

    public ExecutorDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
