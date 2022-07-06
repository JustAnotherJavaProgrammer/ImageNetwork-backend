package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.exception.PostNotFoundException;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.repository.PostRepository;
import de.lukas.imagenetwork.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    List<Post> all() {
        return postService.getAll();
    }

    @GetMapping("/posts/{userId}")
    List<Post> userPosts(@PathVariable Long userId) {
        return postService.getAllForUser(userId);
    }

    @GetMapping("/post/{id}")
    Post one(@PathVariable Long id) {
        return postService.getById(id);
    }

    // TODO: image upload
    @PostMapping("/post")
    Long createPost(@RequestBody PostCreate postCreate) {
        return postService.createPost(postCreate);
    }

    @PatchMapping("/post/{id}")
    Long editPost(@PathVariable Long id, @RequestBody PostModify postModify) {
        return postService.editPost(id, postModify);
    }
}
