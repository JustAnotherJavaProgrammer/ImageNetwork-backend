package de.lukas.imagenetwork;

import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.model.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends ImageNetworkBackendApplicationTests {
    @Test
    public void testUnauthorized() {
        ResponseEntity<User> response = testRestTemplate.getForEntity("/user/{id}", User.class, 1);
        User usr = response.getBody();
        assertNull(usr);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testUserCreation() {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail("test@example.com");
        userCreate.setName("Max Mustermann");
        userCreate.setPassword("Passwort123");
        userCreate.setNickname("johndoe");
        HttpEntity request = new HttpEntity(userCreate);
        ResponseEntity<Long> response = testRestTemplate.postForEntity("/user", request, Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    protected Long createUser(String email, String name, String nickname, String password) {
        UserCreate userCreate = new UserCreate();
        userCreate.setEmail(email);
        userCreate.setName(name);
        userCreate.setPassword(password);
        userCreate.setNickname(nickname);
        HttpEntity request = new HttpEntity(userCreate);
        ResponseEntity<Long> response = testRestTemplate.postForEntity("/user", request, Long.class);
        return response.getBody();
    }

    @Test
    public void testGetOwnUser() {
        String email = "john.doe@example.com";
        String password = "testPassword";
        String name = "John Doe";
        String nickname = "Johnny";
        createUser(email, name, nickname, password);
        // FIXME: depends on testUserCreation
        ResponseEntity<User> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(nickname, user.getNickname());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    public void testGetUserById() {
        String email = "max.mustermann@example.com";
        String password = "password";
        String name = "Max M.";
        String nickname = "mx";
        createUser(email, name, nickname, password);
        String t_email = "example@test.de";
        String t_name = "Maxime Mustermann";
        String t_nickname = "mm";
        Long id = createUser(t_email, t_name, t_nickname, "pwd");
        ResponseEntity<User> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user/" + id, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(t_email, user.getEmail());
        assertEquals(t_name, user.getName());
        assertEquals(t_nickname, user.getNickname());
    }

    @Test
    public void testUserNotFound() {
        String email = "test@t.de";
        String password = "passwd";
        String name = "Test user";
        String nickname = "testcase";
        createUser(email, name, nickname, password);
        // TODO: choose another ID?
        long nonexistent_id = 0; // It usually starts counting at 0, so the user with ID=0 is guaranteed not to exist
        ResponseEntity<String> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user/" + nonexistent_id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUserDelete() {
        String email = "johanna.schmidt@example.com";
        String password = "1234pass567";
        String name = "Johanna Schmidt";
        String nickname = "joha_schmdt";
        createUser(email, name, nickname, password);
        String t_email = "franz.kafka@email.org";
        String t_name = "Franz Kafka";
        String t_nickname = "humor";
        String t_password = "kafkaesque";
        Long t_id = createUser(t_email, t_name, t_nickname, t_password);
        assertNotNull(t_id);
        ResponseEntity<User> getResponse = testRestTemplate.withBasicAuth(email, password).getForEntity("/user/" + t_id, User.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(t_id, getResponse.getBody().getId());
        testRestTemplate.withBasicAuth(t_email, t_password).delete("/user/" + t_id);
        getResponse = testRestTemplate.withBasicAuth(t_email, t_password).getForEntity("/user", User.class);
        assertEquals(getResponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
        ResponseEntity<String> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/user/" + t_id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllUsers() {
        String email = "jan.schroeder@mail.at";
        String password = "lastPasswordEver";
        String name = "Jan Schr\u00f6der";
        String nickname = "j_schroed";
        createUser(email, name, nickname, password);
        ParameterizedTypeReference<PaginatedResponse<User>> responseType = new ParameterizedTypeReference<>() {
        };
        @SuppressWarnings("unchecked")
        ResponseEntity<PaginatedResponse<User>> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/users", (Class<PaginatedResponse<User>>) (Class<?>) PaginatedResponse.class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTotalElements() > 0);
    }
}
