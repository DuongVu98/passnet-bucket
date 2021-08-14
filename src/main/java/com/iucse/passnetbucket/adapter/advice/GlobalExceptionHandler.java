package com.iucse.passnetbucket.adapter.advice;

import com.iucse.passnetbucket.adapter.api.BaseController;
import com.iucse.passnetbucket.domain.exception.BucketNotFoundException;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.exception.FileNameExistedException;
import com.iucse.passnetbucket.domain.exception.FileNotExistException;
import com.iucse.passnetbucket.domain.view.ErrorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorView> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception
           .getBindingResult()
           .getAllErrors()
           .forEach(
              error -> {
                  String fieldName = ((FieldError) error).getField();
                  String errorMessage = error.getDefaultMessage();
                  errors.put(fieldName, errorMessage);
              }
           );

        return returnBadRequest(exception.getMessage(), errors);
    }

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
