package com.iucse.passnetbucket.adapter.advice;

import com.iucse.passnetbucket.adapter.api.BaseController;
import com.iucse.passnetbucket.domain.exception.BucketNotFoundException;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.exception.FileNameExistedException;
import com.iucse.passnetbucket.domain.exception.FileNotExistException;
import com.iucse.passnetbucket.domain.view.ErrorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BucketNotFoundException.class)
    public ResponseEntity<ErrorView> handle(BucketNotFoundException exception) {
        return returnNotFound(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BucketNotFoundException.class)
    public ResponseEntity<ErrorView> handle(CommandNotCompatibleException exception) {
        return returnInternalServer(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FileNameExistedException.class)
    public ResponseEntity<ErrorView> handle(FileNameExistedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorView(exception.getMessage(), HttpStatus.CONFLICT.name()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotExistException.class)
    public ResponseEntity<ErrorView> handle(FileNotExistException exception) {
        return returnNotFound(exception.getMessage());
    }
}
