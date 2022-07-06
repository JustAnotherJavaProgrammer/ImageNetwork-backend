package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.model.FriendCreate;
import de.lukas.imagenetwork.repository.FriendRepository;
import de.lukas.imagenetwork.service.FriendshipService;
import de.lukas.imagenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/friendships")
    List<Friend> all() {
        return friendshipService.getAll();
    }

    @GetMapping("/friends/{id}")
    List<User> userFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @PostMapping("/friend")
    String addFriend(@RequestBody FriendCreate friendCreate) {
        friendshipService.createFriendship(friendCreate);
        return "Done";
    }
}
