package ru.shintar.blog.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tag {

    @Id
    Long id;

    String name;

    @Column("post_id")
    Long postId;

    public Tag(String name, Long postId) {
        this.name = name;
        this.postId = postId;
    }
}