package com.example.meuTreino.model.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public UserNotFoundException(Throwable t) {
        super(t);
    }
}
