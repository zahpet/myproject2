package com.javacourse.myproject2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CryptoNotFoundException extends RuntimeException {
    public CryptoNotFoundException(Integer id) {
        super("Crypto with id " + id + " not found");
    }
}
