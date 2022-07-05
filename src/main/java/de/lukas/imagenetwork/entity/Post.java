package de.lukas.imagenetwork.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

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
    @Column(name = "createdat")
    private Instant createdAt;
    @Column(name = "modifiedat")
    private Instant modifiedAt;

    //    @Type(type = "org.hibernate.type.NumericBooleanType")
//    @Column(columnDefinition = "deleted BOOLEAN DEFAULT FALSE", nullable = false)
//    @ColumnDefault(value = "FALSE")
    private Boolean deleted = false;
}
