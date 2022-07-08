package de.lukas.imagenetwork;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.model.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserTest extends ImageNetworkBackendApplicationTests {
    @Test
    public void testUnauthorized() {
        ResponseEntity<User> response = testRestTemplate.getForEntity("/user/{id}", User.class, 1);
        User usr = response.getBody();
        assertNull(usr);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
    }

    @Test
    public void testUserCreation() {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail("test@example.com");
        userCreate.setName("Max Mustermann");
        userCreate.setPassword("Passwort123");
        userCreate.setNickname("johndoe");
        HttpEntity request = new HttpEntity(userCreate);
        ResponseEntity<Long> response = testRestTemplate.postForEntity("/user", request,Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    protected Long createUser(String email, String name, String nickname, String password) {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail(email);
        userCreate.setName(name);
        userCreate.setPassword(password);
        userCreate.setNickname(nickname);
        HttpEntity request = new HttpEntity(userCreate);
        ResponseEntity<Long> response = testRestTemplate.postForEntity("/user", request,Long.class);
        return response.getBody();
    }

    @Test
    public void testGetOwnUser() {
        String email = "john.doe@example.com";
        String password = "testPassword";
        createUser(email, "John Doe", "Johnny", password);
        // FIXME: depends on testUserCreation
        ResponseEntity<User> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // TODO: validate fields
    }

    @Test
    public void testGetUserById() {
        String email = "max.mustermann@example.com";
        String password = "password";
        createUser(email, "Max M.", "mx", password);
        Long id = createUser("example@test.de", "Maxime Mustermann", "mm", "pwd");
        ResponseEntity<User> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user/"+id, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUserNotFound() {
        // TODO: test requesting a nonexistent user
    }
}
