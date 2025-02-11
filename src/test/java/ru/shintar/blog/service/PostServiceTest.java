package ru.shintar.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.PostServiceTestConfig;
import ru.shintar.blog.model.Post;
import ru.shintar.blog.repository.PostRepository;

import java.util.List;
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

        assertThrows(RuntimeException.class, () -> postService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void save_shouldSavePost() {
        Post post = new Post();
        Long postId = 2L;
        post.setId(postId);
        when(postRepository.save(post)).thenReturn(postId);

        postService.save(post);

        verify(postRepository, times(1)).save(post);
    }

    @Test
    void deletePost_shouldDeletePost() {
        Long postId = 3L;
        Post post = new Post();
        post.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).deleteById(postId);

        postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void getPosts_withTag_shouldReturnPageOfPosts() {
        PageRequest pageable = PageRequest.of(0, 10);
        String tag = "tag1";
        List<Post> posts = List.of(new Post(), new Post());
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        when(postRepository.findByTag(tag, pageable)).thenReturn(postPage);

        Page<Post> result = postService.getPosts(pageable, tag);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(postRepository, times(1)).findByTag(tag, pageable);
    }

    @Test
    void updatePost_shouldUpdatePost() {
        Long postId = 1L;
        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Old Title");

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setTitle("Updated Title");

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        doNothing().when(postRepository).update(updatedPost);

        postService.updatePost(postId, updatedPost);

        assertEquals("Updated Title", existingPost.getTitle());
        verify(postRepository, times(1)).update(updatedPost);
    }

    @Test
    void updatePost_shouldThrowException_WhenPostNotFound() {
        Long postId = 2L;
        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setTitle("Updated Title");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postService.updatePost(postId, updatedPost));
        verify(postRepository, never()).update(updatedPost);
    }
}
