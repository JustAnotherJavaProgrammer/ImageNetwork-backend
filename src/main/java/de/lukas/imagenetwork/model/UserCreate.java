package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreate {
    private String name;
    private String email;
    private String nickname;
    private String password;
}
