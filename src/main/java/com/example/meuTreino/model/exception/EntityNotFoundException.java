package com.example.meuTreino.model.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() { super(); }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable t) {
        super(t);
    }

    public EntityNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
