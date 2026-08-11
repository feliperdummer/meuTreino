package com.example.meuTreino.model.exception;

public class InvalidFieldException extends Exception {
    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String message) {
        super(message);
    }

    public InvalidFieldException(String message, Throwable t) {
        super(message, t);
    }

    public InvalidFieldException(Throwable t) {
        super(t);
    }
}
