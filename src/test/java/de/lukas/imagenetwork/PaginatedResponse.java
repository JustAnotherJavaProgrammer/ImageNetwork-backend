package de.lukas.imagenetwork;
/*
 * Source: https://www.cloudhadoop.com/spring-resttemplate-paginated-response/
 */


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import de.lukas.imagenetwork.entity.Post;
import de.lukas.imagenetwork.model.UserPublic;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PaginatedResponse(@JsonProperty("content") List<T> content,
                             @JsonProperty("number") int number,
                             @JsonProperty("size") int size,
                             @JsonProperty("totalElements") Long totalElements,
                             @JsonProperty("pageable") JsonNode pageable,
                             @JsonProperty("last") boolean last,
                             @JsonProperty("totalPages") int totalPages,
                             @JsonProperty("sort") JsonNode sort,
                             @JsonProperty("first") boolean first,
                             @JsonProperty("empty") boolean empty) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public PaginatedResponse(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PaginatedResponse(List<T> content) {
        super(content);
    }

    public PaginatedResponse() {
        super(new ArrayList<>());
    }

    public static class PaginatedUsers extends PaginatedResponse<UserPublic> {
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PaginatedUsers(@JsonProperty("content") List<UserPublic> content,
                              @JsonProperty("number") int number,
                              @JsonProperty("size") int size,
                              @JsonProperty("totalElements") Long totalElements,
                              @JsonProperty("pageable") JsonNode pageable,
                              @JsonProperty("last") boolean last,
                              @JsonProperty("totalPages") int totalPages,
                              @JsonProperty("sort") JsonNode sort,
                              @JsonProperty("first") boolean first,
                              @JsonProperty("empty") boolean empty) {

            super(content, PageRequest.of(number, size), totalElements);
        }

        public PaginatedUsers(List<UserPublic> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }

        public PaginatedUsers(List<UserPublic> content) {
            super(content);
        }

        public PaginatedUsers() {
            super(new ArrayList<>());
        }
    }

    public static class PaginatedPosts extends PaginatedResponse<Post> {
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PaginatedPosts(@JsonProperty("content") List<Post> content,
                              @JsonProperty("number") int number,
                              @JsonProperty("size") int size,
                              @JsonProperty("totalElements") Long totalElements,
                              @JsonProperty("pageable") JsonNode pageable,
                              @JsonProperty("last") boolean last,
                              @JsonProperty("totalPages") int totalPages,
                              @JsonProperty("sort") JsonNode sort,
                              @JsonProperty("first") boolean first,
                              @JsonProperty("empty") boolean empty) {

            super(content, PageRequest.of(number, size), totalElements);
        }

        public PaginatedPosts(List<Post> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }

        public PaginatedPosts(List<Post> content) {
            super(content);
        }

        public PaginatedPosts() {
            super(new ArrayList<>());
        }
    }
}
