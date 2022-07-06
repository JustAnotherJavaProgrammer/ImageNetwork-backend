package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.enums.Role;
import de.lukas.imagenetwork.exception.IncorrectRoleException;
import de.lukas.imagenetwork.exception.IncorrectUserException;
import de.lukas.imagenetwork.model.FriendCreate;
import de.lukas.imagenetwork.service.FriendshipService;
import de.lukas.imagenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/friendships")
    Page<Friend> all(@AuthenticationPrincipal User user, Pageable pageable) {
        if (user.getRole() != Role.ADMIN)
            throw new IncorrectRoleException(Role.ADMIN);
        return friendshipService.getAll(pageable);
    }

    @GetMapping("/friends")
    Page<User> ownFriends(@AuthenticationPrincipal User user, Pageable pageable) {
        return userService.getFriends(user.getId(), pageable);
    }

    @GetMapping("/friends/{id}")
    Page<User> userFriends(@PathVariable Long id, @AuthenticationPrincipal User user, Pageable pageable) {
        if(id == null)
            id = user.getId();
        return userService.getFriends(id, pageable);
    }

    @PostMapping("/friend")
    String addFriend(@RequestBody FriendCreate friendCreate, @AuthenticationPrincipal User user) {
        if(friendCreate.getUserId() == null)
            friendCreate.setUserId(user.getId());
        if (!Objects.equals(user.getId(), friendCreate.getUserId()) && user.getRole() != Role.ADMIN)
            throw new IncorrectUserException(user, friendCreate.getUserId());
        friendshipService.createFriendship(friendCreate);
        return "Done";
    }
}
