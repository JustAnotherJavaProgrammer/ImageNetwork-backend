package de.lukas.imagenetwork.model;

import de.lukas.imagenetwork.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;

@Data
@NoArgsConstructor
public class UserPublic {
    private @Id Long id;
    private String name;
    private String email;
    private String nickname;
    private Instant createdAt;
    private Instant updatedAt;
    private Role role = Role.USER;
    private Boolean deleted = false;
}
