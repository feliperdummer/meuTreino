package com.example.meuTreino.model.exception;

public class AuthorizationException extends Exception {
    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable t) {
        super(message, t);
    }

    public AuthorizationException(Throwable t) {
        super(t);
    }
}
