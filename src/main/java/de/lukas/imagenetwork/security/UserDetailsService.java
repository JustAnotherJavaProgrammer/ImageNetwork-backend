package de.lukas.imagenetwork.security;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.repository.UserRepository;
import de.lukas.imagenetwork.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return userService.getByEmail(email);
    }


}
