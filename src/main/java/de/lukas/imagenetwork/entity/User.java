package de.lukas.imagenetwork.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String email;
    private String nickname;
    private String password; // TODO: Don't save password as plaintext
    @Column(name="createdat")
    private Instant createdAt;
    @Column(name="updatedat")
    private Instant updatedAt;

//    @Type(type = "org.hibernate.type.NumericBooleanType")
//    @Column(columnDefinition = "deleted BOOLEAN DEFAULT FALSE", nullable = false)
//    @ColumnDefault(value = "FALSE")
    private Boolean deleted = false;
}
