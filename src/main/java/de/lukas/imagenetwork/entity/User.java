package de.lukas.imagenetwork.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boolean deleted;
    @Column(name="createdat")
    private Instant createdAt;
    @Column(name="updatedat")
    private Instant updatedAt;

}
