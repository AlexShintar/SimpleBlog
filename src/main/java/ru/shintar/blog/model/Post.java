package ru.shintar.blog.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    Long id;

    String title;
    String content;

    @Column("image_url")
    String imageUrl;

    transient Integer likesCount;
    transient Integer commentCount;
    transient String tags;
}