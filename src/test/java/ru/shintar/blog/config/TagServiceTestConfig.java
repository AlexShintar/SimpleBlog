package ru.shintar.blog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shintar.blog.repository.TagRepository;
import ru.shintar.blog.service.TagService;

@Configuration
public class TagServiceTestConfig {

    @Bean
    public TagRepository tagRepository() {
        return Mockito.mock(TagRepository.class);
    }

    @Bean
    public TagService tagService() {
        return new TagService(tagRepository());
    }
}