package de.lukas.imagenetwork.security;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.exception.UserNotFoundException;
import de.lukas.imagenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserService userService;

//    UserDetailsService(UserService userService) {
//        this.userService = userService;
//    }
    @Override
    public UserDetails loadUserByUsername(String email)  {
        User user = userService.getByEmail(email);
        if(user.getDeleted())
            throw new UserNotFoundException(user.getId());
        return user;
    }


}
