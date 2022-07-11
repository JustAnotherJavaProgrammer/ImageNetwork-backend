package de.lukas.imagenetwork;

import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.model.PostCreate;
import de.lukas.imagenetwork.model.PostModify;
import de.lukas.imagenetwork.model.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest extends ImageNetworkBackendApplicationTests {
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
    public void testCreatePost() {
        String email = "usermail@provider.it";
        String password = "copyrightInfringement";
        createUser(email, "Mario Mario", "super_mario", password);
        TestRestTemplate testRestTemplate_withAuth = testRestTemplate.withBasicAuth(email, password);
        PostCreate postCreate = new PostCreate();
        postCreate.setComment("A wonderful comment");
        postCreate.setTitle("Title of post");
        postCreate.setImage("Blob of an image");
        ResponseEntity<Long> createResponse = testRestTemplate_withAuth.postForEntity("/post", postCreate, Long.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
    }

    protected Long createPost(String title, String image, String comment, String email, String password) {
        PostCreate postCreate = new PostCreate();
        postCreate.setComment(comment);
        postCreate.setTitle(title);
        postCreate.setImage(image);
        return testRestTemplate.withBasicAuth(email, password).postForEntity("/post", postCreate, Long.class).getBody();
    }

    @Test
    public void testGetPostById() {
        String email = "mailuser@provider.it";
        String password = "infringedCopyright";
        Long userId = createUser(email, "Luigi Luigi", "just_luigi", password);
        TestRestTemplate testRestTemplate_withAuth = testRestTemplate.withBasicAuth(email, password);
        String title = "Nice title";
        String image = "imageblob";
        String comment = "Next comment, please!";
        Long postId = createPost(title, image, comment, email, password);
        ResponseEntity<Post> response = testRestTemplate_withAuth.getForEntity("/post/" + postId, Post.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Post post = response.getBody();
        assertEquals(title, post.getTitle());
        assertEquals(image, post.getImage());
        assertEquals(comment, post.getComment());
        assertEquals(userId, post.getUserId());
        ResponseEntity<PaginatedResponse.PaginatedPosts> getByUserIdResponse = testRestTemplate_withAuth.getForEntity("/posts/" + userId, PaginatedResponse.PaginatedPosts.class);
        assertEquals(HttpStatus.OK, getByUserIdResponse.getStatusCode());
        assertNotNull(getByUserIdResponse.getStatusCode());
        assertNotNull(getByUserIdResponse.getBody());
        assertEquals(1, getByUserIdResponse.getBody().getContent().size());
        assertEquals(postId, getByUserIdResponse.getBody().getContent().get(0).getId());
    }

    @Test
    public void testModifyPost() {
        String email = "max.frisch@oldmail.de";
        String password = "somethingSomething";
        Long userId = createUser(email, "Max Frisch", "maxi", password);
        TestRestTemplate testRestTemplate_withAuth = testRestTemplate.withBasicAuth(email, password);
        Long postId = createPost("Old title", "oldImage", "oldComment", email, password);
        PostModify postModify = new PostModify();
        postModify.setTitle("New title");
        Long editResponse = testRestTemplate_withAuth.patchForObject("/post/" + postId, postModify, Long.class);
        assertEquals(postId, editResponse);
        ResponseEntity<Post> response = testRestTemplate_withAuth.getForEntity("/post/" + postId, Post.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New title", response.getBody().getTitle());
    }

    @Test
    public void testDeletePost() {
        String email = "friedrich-schiller@lit.de";
        String password = "Zisch!";
        Long userId = createUser(email, "Friedrich Schiller", "Fritze", password);
        TestRestTemplate testRestTemplate_withAuth = testRestTemplate.withBasicAuth(email, password);
        Long postId = createPost("My first post", "badImage", "A comment I don't like", email, password);
        // Post exists?
        ResponseEntity<Post> getResponse = testRestTemplate_withAuth.getForEntity("/post/" + postId, Post.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        // Delete post
        testRestTemplate_withAuth.delete("/post/" + postId);
        ResponseEntity<String> secondGetResponse = testRestTemplate_withAuth.getForEntity("/post/" + postId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, secondGetResponse.getStatusCode());
    }
}
