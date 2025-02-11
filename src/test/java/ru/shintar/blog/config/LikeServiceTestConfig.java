package ru.shintar.blog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shintar.blog.repository.LikeRepository;
import ru.shintar.blog.repository.PostRepository;
import ru.shintar.blog.service.LikeService;

@Configuration
public class LikeServiceTestConfig {

    @Bean
    public PostRepository postRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    public LikeRepository likeRepository() {
        return Mockito.mock(LikeRepository.class);
    }

    @Bean
    public LikeService likeService() {
        return new LikeService(likeRepository());
    }
}