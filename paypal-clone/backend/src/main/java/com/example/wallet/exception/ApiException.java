package com.example.wallet.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
