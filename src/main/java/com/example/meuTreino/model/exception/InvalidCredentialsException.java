package com.example.meuTreino.model.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable t) {
        super(message, t);
    }

    public InvalidCredentialsException(Throwable t) {
        super(t);
    }
}
