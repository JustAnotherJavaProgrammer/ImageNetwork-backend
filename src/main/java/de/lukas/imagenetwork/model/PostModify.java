package de.lukas.imagenetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Id;

@Data
@NoArgsConstructor
public class PostModify {
    private @Nullable String image;
    private @Nullable String comment;
    private @Nullable String title;
}
