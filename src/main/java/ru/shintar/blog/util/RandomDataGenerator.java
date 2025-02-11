package ru.shintar.blog.util;


import net.datafaker.Faker;
import ru.shintar.blog.model.Comment;
import ru.shintar.blog.model.Post;

import java.time.LocalDateTime;

public class RandomDataGenerator {

    private static final Faker faker = new Faker();

    public static Post generateRandomPost() {
        Post post = new Post();
        post.setTitle(faker.company().name());
        post.setContent(faker.lorem().sentence(100));
        post.setImageUrl("https://picsum.photos/id/"
                + faker.random().nextInt(90)
                + "/" + faker.random().nextInt(200, 300)
                + "/" + faker.random().nextInt(100, 200));

        StringBuilder tagString = new StringBuilder();
        tagString.append(faker.animal().name());
        for (int j = 0; j < faker.random().nextInt(10); j++) {
            tagString.append(", ").append(faker.animal().name());
        }
        post.setTags(tagString.toString());

        return post;
    }

    public static Comment generateRandomComment(Long postId) {
        Comment comment = new Comment();
        comment.setContent(faker.lorem().sentence(20));
        comment.setPostId(postId);
        comment.setUpdatedAt(LocalDateTime.now());

        return comment;
    }
}