package ru.shintar.blog.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
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

    private final PostRepository postRepository;
    private final TagService tagService;
    private final LikeService likeService;
    private final CommentService commentService;
    Faker faker = new Faker();

    public Page<Post> getPosts(Pageable pageable, String tag) {
        Page<Post> posts = (tag == null || tag.isBlank())
                ? postRepository.findAll(pageable)
                : postRepository.findByTag(tag, pageable);

        posts.forEach(this::process);
        return posts;
    }

    public Post getPostById(Long id) {
        Post post = postRepository.findById(id)
                .map(this::process)
                .orElseThrow(() -> new EntityNotFoundException("Пост не найден"));
        return post;
    }

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
        tagService.save(post);
    }

    @Transactional
    public void rnd() {
        for (int i = 0; i < 30; i++) {
            Post post = new Post();
            post.setTitle(faker.company().name());
            post.setContent(faker.lorem().sentence(20));
            post.setImageUrl("https://picsum.photos/id/"
                    + faker.random().nextInt(90)
                    + "/" + faker.random().nextInt(200, 300)
                    + "/" + faker.random().nextInt(100, 200));
//            post.setUpdatedAt(LocalDateTime.now());

            StringBuilder tagString = new StringBuilder();
            tagString.append(faker.animal().name());

            for (int j = 0; j < faker.random().nextInt(10); j++) {
                tagString.append(", ").append(faker.animal().name());
            }
            post.setTags(tagString.toString());
            save(post);

            for (int j = 0; j < faker.random().nextInt(10); j++) {
                commentService.addComment(post.getId(), faker.lorem().sentence(10));
            }
        }
    }

    private Post process(Post post) {
        post.setCommentCount(commentService.getCommentCount(post));
        post.setLikesCount(likeService.getLikeCount(post));
        post.setTags(tagService.getTags(post));
        return post;
    }

    @Transactional
    public void updatePost(Long id, Post updatedPost) {
        Post post = getPostById(id);
        tagService.deleteTagsByPostId(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setImageUrl(updatedPost.getImageUrl());
        post.setUpdatedAt(LocalDateTime.now());
        post.setTags(updatedPost.getTags());
        save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        commentService.deleteCommentsByPostId(id);
        likeService.deleteLikesByPostId(id);
        tagService.deleteTagsByPostId(id);
        postRepository.deleteById(id);
    }
}