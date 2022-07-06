package de.lukas.imagenetwork.advice;

import de.lukas.imagenetwork.exception.IncorrectRoleException;
import de.lukas.imagenetwork.exception.IncorrectUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IncorrectRoleAdvice {
    @ResponseBody
    @ExceptionHandler(IncorrectRoleException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String IncorrectUserHandler(IncorrectRoleException ex) {
        return ex.getMessage();
    }
}
