package de.lukas.imagenetwork.repository;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM friends WHERE userId = :userId")
    List<Friend> findAllByUserId(@Param("userId") Long userId);
}
