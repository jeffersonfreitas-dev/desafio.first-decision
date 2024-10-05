package dev.jeffersonfreitas.register_api.exceptions;

public class AlreadyRegisteredException extends RuntimeException{

    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
