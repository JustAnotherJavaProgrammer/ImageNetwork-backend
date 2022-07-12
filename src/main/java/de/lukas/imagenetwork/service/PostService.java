package de.lukas.imagenetwork.service;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.exception.PostNotFoundException;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

//    public List<Post>  getAll() {
//        return postRepository.findAll();
//    }

    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

//    public List<Post> getAllForUser(Long userId) {
//        return postRepository.findAllByUserId(userId);
//    }

    public Page<Post> getAllForUser(Long userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable);
    }

    public Page<Post> getAllDeletedForUser(Long userId, Pageable pageable) {
        return postRepository.findDeletedByUserId(userId, pageable);
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

    public Long editPost(Long id, PostModify postModify, @Nullable Long userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if(userId != null && !Objects.equals(post.getUserId(), userId))
            throw new RuntimeException(post.getUserId().toString());
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

    public void deletePost(Long id, @Nullable Long userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if(userId != null && !Objects.equals(post.getUserId(), userId))
            throw new RuntimeException(post.getUserId().toString());
        post.setDeleted(true);
        postRepository.save(post);
    }

    public void recoverPost(Long id, @Nullable Long userId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if(userId != null && !Objects.equals(post.getUserId(), userId))
            throw new RuntimeException(post.getUserId().toString());
        post.setDeleted(false);
        postRepository.save(post);
    }
}
