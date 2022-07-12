package de.lukas.imagenetwork.repository;

import de.lukas.imagenetwork.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE userId = :userId AND deleted IS NOT TRUE")
    List<Post> findAllByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE userId = :userId AND deleted IS NOT TRUE")
    Page<Post> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE userId = :userId AND deleted IS TRUE")
    Page<Post> findDeletedByUserId(@Param("userId") Long userId, Pageable pageable);
}
