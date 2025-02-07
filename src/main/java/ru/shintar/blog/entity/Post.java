package ru.shintar.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String content;

    @Column(name = "image_url")
    String imageUrl;

    @Transient
    Integer likesCount;

    @Transient
    Integer commentCount;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Transient
    String tags;
}
