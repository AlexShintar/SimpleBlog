package ru.shintar.blog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shintar.blog.repository.PostRepository;
import ru.shintar.blog.service.CommentService;
import ru.shintar.blog.service.LikeService;
import ru.shintar.blog.service.PostService;
import ru.shintar.blog.service.TagService;

@Configuration
public class PostServiceTestConfig {

    @Bean
    public PostRepository postRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    public TagService tagService() {
        return Mockito.mock(TagService.class);
    }

    @Bean
    public LikeService likeService() {
        return Mockito.mock(LikeService.class);
    }

    @Bean
    public CommentService commentService() {
        return Mockito.mock(CommentService.class);
    }

    @Bean
    public PostService postService(PostRepository postRepository,
                                   TagService tagService,
                                   LikeService likeService,
                                   CommentService commentService) {
        return new PostService(postRepository, tagService, likeService, commentService);
    }
}
