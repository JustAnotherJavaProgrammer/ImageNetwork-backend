package de.lukas.imagenetwork;

import de.lukas.imagenetwork.entity.Friend;
import de.lukas.imagenetwork.entity.User;
import de.lukas.imagenetwork.model.FriendCreate;
import de.lukas.imagenetwork.model.UserCreate;
import de.lukas.imagenetwork.model.UserPublic;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class FriendTest extends ImageNetworkBackendApplicationTests {
    // TODO: code copied from UserTest.java
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
    public void createFriendshipTest() {
        String email_a = "test.user@e-mail.co.uk";
        String password_a = "wonderful_password";
        Long id_a = createUser(email_a, "Robert'); DROP TABLE Students;--", "Bobby Tables", password_a);
        String email_b = "another.user@e-mail.co.uk";
        String password_b = "seopsdfjsg0ij";
        Long id_b = createUser(email_b, "Phil Smith", "phsmith", password_b);
        // Started without friends?
        ResponseEntity<PaginatedResponse.PaginatedUsers> response = testRestTemplate.withBasicAuth(email_a, password_a).getForEntity("/friends", PaginatedResponse.PaginatedUsers.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getContent().size());
        // A adds B as friend
        FriendCreate friendCreate = new FriendCreate();
        friendCreate.setFriendId(id_b);
        ResponseEntity<String> createResponse = testRestTemplate.withBasicAuth(email_a, password_a).postForEntity("/friend", friendCreate, String.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        // A has B as their friend
        ResponseEntity<PaginatedResponse.PaginatedUsers> getResponse = testRestTemplate.withBasicAuth(email_a, password_a).getForEntity("/friends", PaginatedResponse.PaginatedUsers.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(1, getResponse.getBody().getContent().size());
        UserPublic friend = getResponse.getBody().getContent().get(0);
        assertEquals(id_b, friend.getId());
        // Check if friendship is truly one-directional
        ResponseEntity<PaginatedResponse.PaginatedUsers> getSecondResponse = testRestTemplate.withBasicAuth(email_b, password_b).getForEntity("/friends", PaginatedResponse.PaginatedUsers.class);
        assertEquals(HttpStatus.OK, getSecondResponse.getStatusCode());
        assertNotNull(getSecondResponse.getBody());
        assertEquals(0, getSecondResponse.getBody().getContent().size());
    }

    @Test
    public void testGetFriendsForUser() {
        String email_a = "klaus-kraemer@demail.com";
        String password_a = "nvw dgWEWarv fgeWE";
        Long id_a = createUser(email_a, "Klaus Kraemer", "klaus", password_a);
        String email_b = "hans.schmitz@demail.com";
        String password_b = "dgJFHKfDfgAFSGFK:ULZDGSFEFEWR3RGgdge";
        Long id_b = createUser(email_b, "Hans Schmitz", "hanss", password_b);
        // B adds A as friend
        FriendCreate friendCreate = new FriendCreate();
        friendCreate.setFriendId(id_a);
        ResponseEntity<String> createResponse = testRestTemplate.withBasicAuth(email_b, password_b).postForEntity("/friend", friendCreate, String.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        // a makes a request to check ig B has them (A) as their friend
        ResponseEntity<PaginatedResponse.PaginatedUsers> getResponse = testRestTemplate.withBasicAuth(email_a, password_a).getForEntity("/friends/" + id_b, PaginatedResponse.PaginatedUsers.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(1, getResponse.getBody().getContent().size());
        UserPublic friend = getResponse.getBody().getContent().get(0);
        assertEquals(id_a, friend.getId());
    }

    @Test
    public void testUnauthorizedGetAllFriendships() {
        String email = "email.address@wonderful-provider.com";
        String password = "ofhewfwg04w";
        createUser(email, "Emilia Address", "email-address", password);
        ResponseEntity<String> response = testRestTemplate.withBasicAuth(email, password).getForEntity("/friendships/", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
