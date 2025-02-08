package ru.shintar.blog.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository repository;
    private final TagService tagService;
    Faker faker = new Faker();

    public Page<Post> getPosts(Pageable pageable, String tag) {
        Page<Post> posts = (tag == null || tag.isBlank())
                ? repository.findAll(pageable)
                : repository.findByTag(tag, pageable);

        posts.forEach(this::process);
        return posts;
    }

    @Transactional
    public void save(Post post) {
        repository.save(post);
        tagService.save(post);
    }

    @Transactional
    public void rnd() {
        for (int i = 0; i < 30; i++) {
            Post post = new Post();
            post.setTitle(faker.company().name());
            post.setContent(faker.lorem().sentence(20));
            post.setImageUrl("https://picsum.photos/" + faker.random().nextInt(100, 300)
                    + "/" + faker.random().nextInt(100, 200));
            post.setUpdatedAt(LocalDateTime.now());
            StringBuilder tagString = new StringBuilder();
            for (int j = 0; j < faker.random().nextInt(5); j++) {
                tagString.append(", ").append(faker.animal().name());
            }
            post.setTags(String.valueOf(tagString));
            save(post);
        }
    }

    private void process(Post post) {
        post.setCommentCount(faker.random().nextInt(10));
        post.setLikesCount(faker.random().nextInt(10));
        post.setTags(tagService.getTags(post));
    }
}