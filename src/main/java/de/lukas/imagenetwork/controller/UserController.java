package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.exception.UserNotFoundException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/user")
    String newUser(@RequestBody UserCreate userCreate) {
        // User.from... ?
        User user = new User();
        user.setName(userCreate.getName());
        user.setEmail(userCreate.getEmail());
        user.setNickname(userCreate.getNickname());
        user.setPassword(userCreate.getPassword());
        repository.save(user);
        return "Done";

    }
}
