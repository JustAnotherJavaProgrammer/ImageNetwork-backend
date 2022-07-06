package de.lukas.imagenetwork.service;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.exception.PostNotFoundException;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post>  getAll() {
        return postRepository.findAll();
    }

    public List<Post> getAllForUser(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    public Post getById(Long id) throws PostNotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Long createPost(PostCreate postCreate) {
        Post post = new Post();
        post.setUserId(postCreate.getUserId());
        post.setImage(postCreate.getImage());
        post.setComment(postCreate.getComment());
        post.setTitle(postCreate.getTitle());
        post.setCreatedAt(Instant.now());
        postRepository.save(post);
        return post.getId();
    }

    public Long editPost(Long id, PostModify postModify) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (postModify.getImage() != null)
            post.setImage(postModify.getImage());
        if (postModify.getComment() != null)
            post.setComment(postModify.getComment());
        if (postModify.getTitle() != null)
            post.setTitle(postModify.getTitle());
        post.setModifiedAt(Instant.now());
        postRepository.save(post);
        return post.getId();
    }
}
