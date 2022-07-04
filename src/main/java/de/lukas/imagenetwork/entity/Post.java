package de.lukas.imagenetwork.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "posts")
@NoArgsConstructor
public class Post {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "userid")
    private Long userId;
    private String image;
    private String comment;
    private String title;
    @Column(name="createdat")
    private Instant createdAt;
    @Column(name = "modifiedat")
    private Instant modifiedAt;
    private Boolean deleted;
}
