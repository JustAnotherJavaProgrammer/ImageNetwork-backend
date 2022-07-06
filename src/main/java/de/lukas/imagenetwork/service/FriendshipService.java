package de.lukas.imagenetwork.service;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.model.FriendCreate;
import de.lukas.imagenetwork.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendRepository friendRepository;

    public List<Friend> getAll() {
        return friendRepository.findAll();
    }

    public void createFriendship(FriendCreate friendCreate) {
        Friend friend = new Friend();
        friend.setUserId(friendCreate.getUserId());
        friend.setFriendId(friendCreate.getFriendId());
        friendRepository.save(friend);
    }
}