package de.lukas.imagenetwork.exception;

import de.lukas.imagenetwork.entity.User;

public class IncorrectUserException extends RuntimeException {
    public IncorrectUserException(User u, Long correctId) {
        super("This action affects user " + correctId + " and cannot be performed by user " + u.getId());
    }
}
