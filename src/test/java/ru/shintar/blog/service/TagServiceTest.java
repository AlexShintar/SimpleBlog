package ru.shintar.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.TagServiceTestConfig;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.entity.Tag;
import ru.shintar.blog.repository.TagRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TagServiceTestConfig.class})
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(tagRepository);
    }

    @Test
    void save_shouldSaveTags() {
        Post post = new Post();
        post.setTags("java, spring, testing");

        tagService.save(post);

        verify(tagRepository, times(3)).save(any(Tag.class));
    }

    @Test
    void save_whenTagsAreEmpty_shouldNotSaveTags() {
        Post post = new Post();
        post.setTags("");

        tagService.save(post);

        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    void getTags_shouldReturnCommaSeparatedTags() {
        Post post = new Post();
        List<Tag> tags = List.of(new Tag("java", post), new Tag("spring", post));
        when(tagRepository.findAllByPost(post)).thenReturn(tags);

        String result = tagService.getTags(post);

        assertEquals("java, spring", result);
    }

    @Test
    void getTags_whenNoTags_shouldReturnEmptyString() {
        Post post = new Post();
        when(tagRepository.findAllByPost(post)).thenReturn(List.of());

        String result = tagService.getTags(post);

        assertEquals("", result);
    }

    @Test
    void deleteTagsByPostId_shouldDeleteTags() {
        Long postId = 3L;
        doNothing().when(tagRepository).deleteByPostId(postId);

        tagService.deleteTagsByPostId(postId);

        verify(tagRepository, times(1)).deleteByPostId(postId);
    }
}