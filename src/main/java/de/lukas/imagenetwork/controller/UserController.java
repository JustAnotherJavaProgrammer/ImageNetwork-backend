package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.exception.UserNotFoundException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.repository.UserRepository;
import de.lukas.imagenetwork.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/users")
    List<User> all() {
        return userService.getAll();
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
    String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Done";
    }
}
