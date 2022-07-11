package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.enums.Role;
import de.lukas.imagenetwork.exception.IncorrectUserException;
import de.lukas.imagenetwork.exception.PostNotFoundException;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    Page<Post> all(Pageable pageable) {
        return postService.getAll(pageable);
    }

    @GetMapping("/posts/{userId}")
    Page<Post> userPosts(@PathVariable Long userId, Pageable pageable) {
        return postService.getAllForUser(userId, pageable);
    }

    @GetMapping("/post/{id}")
    Post one(@PathVariable Long id) {
        Post post = postService.getById(id);
        if(post.getDeleted())
            throw new PostNotFoundException(id);
        return post;
    }

    // TODO: image upload
    @PostMapping("/post")
    Long createPost(@RequestBody PostCreate postCreate, @AuthenticationPrincipal User user) {
        if (postCreate.getUserId() == null)
            postCreate.setUserId(user.getId());
        if (!Objects.equals(user.getId(), postCreate.getUserId()) && user.getRole() != Role.ADMIN)
            throw new IncorrectUserException(user, postCreate.getUserId());
        return postService.createPost(postCreate);
    }

    @PatchMapping("/post/{id}")
    Long editPost(@PathVariable Long id, @RequestBody PostModify postModify, @AuthenticationPrincipal User user) {
        try {
            return postService.editPost(id, postModify, user.getRole() == Role.ADMIN ? null : user.getId());
        } catch (RuntimeException e) {
            throw new IncorrectUserException(user, Long.valueOf(e.getMessage()));
        }
    }

    @DeleteMapping("/post/{id}")
    String delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            postService.deletePost(id, user.getRole() == Role.ADMIN ? null : user.getId());
            return "Done";
        } catch (RuntimeException e) {
            throw new IncorrectUserException(user, Long.valueOf(e.getMessage()));
        }
    }
}
