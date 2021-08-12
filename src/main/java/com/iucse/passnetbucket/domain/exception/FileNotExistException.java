package com.iucse.passnetbucket.domain.exception;

public class FileNotExistException extends RuntimeException {
    public FileNotExistException(String message) {
        super(message);
    }

    public FileNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
