package ru.shintar.blog.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository repository;

    Faker faker = new Faker();

    public List<Post> findAll() {
        var posts = repository.findAll();
        posts.forEach(this::process);
        return posts;
    }

    @Transactional
    public void save(Post post) {
        repository.save(post);
    }

    @Transactional
    public void rnd() {
        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setTitle(faker.company().name());
            post.setContent(faker.lorem().sentence(20));
            post.setImageUrl("https://picsum.photos/" + faker.random().nextInt(100, 300)
                    + "/" + faker.random().nextInt(100, 200));
            post.setUpdatedAt(LocalDateTime.now());

            for (int j = 0; j < faker.random().nextInt(5); j++) {
            }
            save(post);
        }
    }

    private void process(Post post) {
        post.setCommentCount(faker.random().nextInt(10));
        post.setLikesCount(faker.random().nextInt(10));
    }

}
