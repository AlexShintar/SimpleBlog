package ru.shintar.blog.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.LikeServiceTestConfig;
import ru.shintar.blog.entity.Like;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.LikeRepository;
import ru.shintar.blog.repository.PostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LikeServiceTestConfig.class})
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(postRepository, likeRepository);
    }

    @Test
    void likePost_whenPostExists_shouldSaveLike() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        likeService.likePost(postId);

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void likePost_whenPostDoesNotExist_shouldThrowException() {
        Long postId = 2L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> likeService.likePost(postId));
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    void getLikeCount_shouldReturnLikeCount() {
        Post post = new Post();
        when(likeRepository.countByPost(post)).thenReturn(5);

        int count = likeService.getLikeCount(post);

        assertEquals(5, count);
        verify(likeRepository, times(1)).countByPost(post);
    }

    @Test
    void deleteLikesByPostId_shouldDeleteLikes() {
        Long postId = 3L;
        doNothing().when(likeRepository).deleteByPostId(postId);

        likeService.deleteLikesByPostId(postId);

        verify(likeRepository, times(1)).deleteByPostId(postId);
    }
}
