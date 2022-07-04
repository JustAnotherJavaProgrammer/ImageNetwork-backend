package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendCreate {
    private Long userId;
    private Long friendId;
}
