package de.lukas.imagenetwork.service;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.exception.UserNotFoundException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.repository.FriendRepository;
import de.lukas.imagenetwork.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

//    public List<User> getAll() {
//        return userRepository.findAll();
//    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);
        return user;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Long createUser(UserCreate userCreate) {
        User user = new User();
        user.setName(userCreate.getName());
        user.setEmail(userCreate.getEmail());
        user.setNickname(userCreate.getNickname());
        user.setPassword(encoder.encode(userCreate.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(user.getCreatedAt());
        userRepository.save(user);
        return user.getId();
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setDeleted(true);
        userRepository.save(user);
    }

//    public List<User> getFriends(Long userId) {
//        List<Friend> list = friendRepository.findAllByUserId(userId);
//        return userRepository.findAllById(list.stream().map(Friend::getFriendId).toList());
//    }

    public Page<User> getFriends(Long userId, Pageable pageable) {
        List<Friend> list = friendRepository.findAllByUserId(userId);
        List<User> users = userRepository.findAllById(list.stream().map(Friend::getFriendId).toList());
        return new PageImpl<>(users.subList((int) Math.min(pageable.getOffset(), users.size()), (int) Math.min(pageable.getOffset() + pageable.getPageSize(), users.size())), pageable, (int) Math.ceil(users.size() / ((double) pageable.getPageSize())));
    }
}
