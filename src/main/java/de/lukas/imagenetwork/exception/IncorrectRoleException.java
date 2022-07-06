package de.lukas.imagenetwork.exception;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.enums.Role;

public class IncorrectRoleException extends RuntimeException {
    public IncorrectRoleException(Role correctRole) {
        super("This action can only be performed by users with role " + correctRole);
    }
}
