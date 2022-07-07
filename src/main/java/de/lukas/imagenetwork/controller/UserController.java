package de.lukas.imagenetwork.controller;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.enums.Role;
import de.lukas.imagenetwork.exception.IncorrectUserException;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final AuthenticationManager authManager;

    @GetMapping("/users")
    Page<User> all(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/user")
    User currentUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/user")
    Long newUser(@RequestBody UserCreate userCreate, HttpServletRequest req) {
        Long id = userService.createUser(userCreate);
        /* Begin automatic authentication after account creation */
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userCreate.getEmail(),userCreate.getPassword());
//        Authentication auth = authManager.authenticate(authToken);
//
//        SecurityContext secContext = SecurityContextHolder.getContext();
//        secContext.setAuthentication(auth);
//        HttpSession session = req.getSession();
//        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, secContext);
        /* End automatic authentication */
        return id;
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if(user.getRole() != Role.ADMIN && !Objects.equals(id, user.getId()))
            throw new IncorrectUserException(user, id);
        userService.deleteUser(id);
        return "Done";
    }
}
