package com.example.meuTreino.model.exception;

public class ExistingUserException extends Exception{
    public ExistingUserException() {
        super();
    }

    public ExistingUserException(String message) {
        super(message);
    }

    public ExistingUserException(String message, Throwable t) {
        super(message, t);
    }

    public ExistingUserException(Throwable t) {
        super(t);
    }
}
