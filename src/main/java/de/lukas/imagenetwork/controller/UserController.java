package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.enums.Role;
import de.lukas.imagenetwork.exception.IncorrectUserException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/users")
//    List<User> all() {
//        return userService.getAll();
//    }

    @GetMapping("/users")
    Page<User> all(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/user")
    User currentUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/user")
    Long newUser(@RequestBody UserCreate userCreate) {
        return  userService.createUser(userCreate);
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if(user.getRole() != Role.ADMIN && !Objects.equals(id, user.getId()))
            throw new IncorrectUserException(user, id);
        userService.deleteUser(id);
        return "Done";
    }
}
