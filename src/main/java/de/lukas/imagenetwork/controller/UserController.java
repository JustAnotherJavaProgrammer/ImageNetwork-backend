package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.exception.UserNotFoundException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@RestController
@NoArgsConstructor
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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/user")
    String newUser(@RequestBody UserCreate userCreate) {
        // User.from... ?
        User user = new User();
        user.setName(userCreate.getName());
        user.setEmail(userCreate.getEmail());
        user.setNickname(userCreate.getNickname());
        user.setPassword("{bcrypt}" + encoder.encode(userCreate.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(user.getCreatedAt());
        repository.save(user);
        return "Done";
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setDeleted(true);
        repository.save(user);
        return "Done";
    }
}
