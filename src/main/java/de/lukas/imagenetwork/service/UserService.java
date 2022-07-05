package de.lukas.imagenetwork.service;

import de.lukas.imagenetwork.repository.FriendRepository;
import de.lukas.imagenetwork.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    private  UserRepository userRepository;
    private  FriendRepository friendRepository;


}
