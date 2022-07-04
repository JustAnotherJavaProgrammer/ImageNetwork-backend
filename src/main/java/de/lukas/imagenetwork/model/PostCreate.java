package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreate {
    private Long userId;
    private String image;
    private String comment;
    private String title;
}
