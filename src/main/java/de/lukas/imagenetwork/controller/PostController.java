package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.exception.PostNotFoundException;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostRepository repository;

    PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/posts")
    List<Post> all() {
        return repository.findAll();
    }

    @GetMapping("/posts/{userId}")
    List<Post> userPosts(@PathVariable Long userId) {
        return repository.findAllByUserId(userId);
    }

    @GetMapping("/post/{id}")
    Post one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    // TODO: image upload
    @PostMapping("/post")
    String createPost(@RequestBody PostCreate postCreate) {
        Post post = new Post();
        post.setUserId(postCreate.getUserId());
        post.setImage(postCreate.getImage());
        post.setComment(postCreate.getComment());
        post.setTitle(postCreate.getTitle());
        repository.save(post);
        return "Done";
    }

    @PatchMapping("/post/{id}")
    String editPost(@PathVariable Long id, @RequestBody PostModify postModify) {
        Post post = repository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (postModify.getImage() != null)
            post.setImage(postModify.getImage());
        if (postModify.getComment() != null)
            post.setComment(postModify.getComment());
        if (postModify.getTitle() != null)
            post.setTitle(postModify.getTitle());
        repository.save(post);
        return "Done";
    }
}
