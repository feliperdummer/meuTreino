package com.example.meuTreino.model.exception;

public class TreinoNotFoundException extends Exception {
    public TreinoNotFoundException() {
        super();
    }

    public TreinoNotFoundException(String message) {
        super(message);
    }

    public TreinoNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public TreinoNotFoundException(Throwable t) {
        super(t);
    }
}
