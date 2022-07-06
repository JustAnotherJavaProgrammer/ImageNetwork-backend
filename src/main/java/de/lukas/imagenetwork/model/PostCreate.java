package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class PostCreate {
    @Nullable private Long userId;
    private String image;
    private String comment;
    private String title;
}
