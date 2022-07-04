package de.lukas.imagenetwork.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(Long id) {
        super("Could not find post " + id);
    }
}
