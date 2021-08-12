package com.iucse.passnetbucket.domain.exception;

public class FileNameExistedException extends RuntimeException {
    public FileNameExistedException(String message) {
        super(message);
    }

    public FileNameExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
