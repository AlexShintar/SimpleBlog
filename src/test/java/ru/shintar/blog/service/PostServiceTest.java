package ru.shintar.blog.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.PostServiceTestConfig;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.PostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostServiceTestConfig.class})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(postRepository);
    }

    @Test
    void getPostById_whenPostExists_shouldReturnPost() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_whenPostDoesNotExist_shouldThrowException() {
        Long postId = 2L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void save_shouldSavePost() {
        Post post = new Post();
        when(postRepository.save(post)).thenReturn(post);

        postService.save(post);

        verify(postRepository, times(1)).save(post);
    }

    @Test
    void deletePost_shouldDeletePost() {
        Long postId = 3L;
        Post post = new Post();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(post);

        postService.deletePost(postId);

        verify(postRepository, times(1)).delete(post);
    }
}
