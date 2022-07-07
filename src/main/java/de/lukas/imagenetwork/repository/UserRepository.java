package de.lukas.imagenetwork.repository;

import de.lukas.imagenetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

//    public Page<User> findAllById(List<Long> ids, Pageable pageable);
}
