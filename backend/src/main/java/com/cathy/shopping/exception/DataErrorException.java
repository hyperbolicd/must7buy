package com.cathy.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataErrorException extends RuntimeException {

    public DataErrorException(String message) {
        super(message);
    }

}
