package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.model.FriendCreate;
import de.lukas.imagenetwork.repository.FriendRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendController {
    private final FriendRepository repository;


    FriendController(FriendRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/friendships")
    List<Friend> all() {
        return repository.findAll();
    }

    @GetMapping("/friends/{id}")
    List<Friend> userFriends(@PathVariable Long id) {


        var list = repository.findAllByUserId(id);


        return repository.findAll(list.stream().map(f -> f.getFriendId()))
    }

    @PostMapping("/friend")
    String addFriend(@RequestBody FriendCreate friendCreate) {
        Friend friend = new Friend();
        friend.setUserId(friendCreate.getUserId());
        friend.setFriendId(friendCreate.getFriendId());
        repository.save(friend);
        return "Done";
    }
}
