package ru.shintar.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.LikeServiceTestConfig;
import ru.shintar.blog.model.Like;
import ru.shintar.blog.model.Post;
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

    private Post testPost;
    @BeforeEach
    void setUp() {
        Mockito.reset(postRepository, likeRepository);
        testPost = new Post();
        testPost.setId(1L);

    }

    @Test
    void likePost_whenPostExists_shouldSaveLike() {

        testPost.setId(testPost.getId());
        when(postRepository.findById(testPost.getId())).thenReturn(Optional.of(testPost));

        likeService.likePost(testPost.getId());

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void getLikeCount_shouldReturnLikeCount() {

        when(likeRepository.countByPostId(testPost.getId())).thenReturn(5);

        int count = likeService.getLikeCount(testPost.getId());

        assertEquals(5, count);
        verify(likeRepository, times(1)).countByPostId(testPost.getId());
    }

    @Test
    void deleteLikesByPostId_shouldDeleteLikes() {
        Long postId = 3L;
        doNothing().when(likeRepository).deleteByPostId(postId);

        likeService.deleteLikesByPostId(postId);

        verify(likeRepository, times(1)).deleteByPostId(postId);
    }
}
