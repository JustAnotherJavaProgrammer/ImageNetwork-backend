package de.lukas.imagenetwork.repository;

import de.lukas.imagenetwork.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE userId = :userId")
    List<Post> findAllByUserId(@Param("userId") Long userId);
}
