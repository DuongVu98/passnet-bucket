package com.iucse.passnetbucket.domain.exception;

public class CommandNotCompatibleException extends RuntimeException {
    public CommandNotCompatibleException(String message) {
        super(message);
    }

    public CommandNotCompatibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
