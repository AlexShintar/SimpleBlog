package ru.shintar.blog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shintar.blog.repository.CommentRepository;
import ru.shintar.blog.repository.PostRepository;
import ru.shintar.blog.service.CommentService;

@Configuration
public class CommentsServiceTestConfig {
    @Bean
    public CommentService commentsService(CommentRepository commentRepository, PostRepository postRepository) {
        return new CommentService(commentRepository, postRepository);
    }
    @Bean
    public CommentRepository commentsRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public PostRepository postRepository() {
        return Mockito.mock(PostRepository.class);
    }
}