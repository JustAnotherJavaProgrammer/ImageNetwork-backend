package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class FriendCreate {
    @Nullable private Long userId;
    private Long friendId;
}
