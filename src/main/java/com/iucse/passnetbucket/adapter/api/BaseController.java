package com.iucse.passnetbucket.adapter.api;

import com.iucse.passnetbucket.domain.view.ErrorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<Object> returnOk() {
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<Object> returnCreated() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    protected <T> ResponseEntity<T> returnOk(T body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<ErrorView> returnNotFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorView(message, HttpStatus.NOT_FOUND.name()));
    }

    protected ResponseEntity<ErrorView> returnBadRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorView(message, HttpStatus.BAD_REQUEST.name()));
    }

    protected ResponseEntity<ErrorView> returnBadRequest(String message, Object body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorView(message, HttpStatus.BAD_REQUEST.name(), body));
    }
}
