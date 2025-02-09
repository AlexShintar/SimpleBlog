package ru.shintar.blog.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.shintar.blog.config.CommentsServiceTestConfig;
import ru.shintar.blog.entity.Comment;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.CommentRepository;
import ru.shintar.blog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CommentsServiceTestConfig.class})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private Post testPost;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        Mockito.reset(commentRepository);
        Mockito.reset(postRepository);

        testPost = new Post();
        testPost.setId(1L);

        testComment = new Comment();
        testComment.setId(1L);
        testComment.setContent("Test Comment");
        testComment.setPost(testPost);
        testComment.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getComments_ShouldReturnListOfComments() {
        when(commentRepository.findAllByPost(testPost)).thenReturn(List.of(testComment));

        List<Comment> comments = commentService.getComments(testPost);

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.get(0).getContent());
        verify(commentRepository, times(1)).findAllByPost(testPost);
    }

    @Test
    void addComment_ShouldSaveComment() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        commentService.addComment(1L, "New Comment");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void addComment_ShouldThrowException_WhenPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.addComment(1L, "New Comment"));

        assertEquals("Пост не найден", exception.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void updateComment_ShouldUpdateAndReturnPostId() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));

        Long postId = commentService.updateComment(1L, "Updated Comment");

        assertEquals(1L, postId);
        assertEquals("Updated Comment", testComment.getContent());
        verify(commentRepository, times(1)).save(testComment);
    }

    @Test
    void updateComment_ShouldThrowException_WhenCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> commentService.updateComment(1L, "Updated Comment"));

        assertEquals("Комментарий не найден", exception.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void deleteComment_ShouldDeleteAndReturnPostId() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));

        Long postId = commentService.deleteComment(1L);

        assertEquals(1L, postId);
        verify(commentRepository, times(1)).delete(testComment);
    }

    @Test
    void deleteComment_ShouldThrowException_WhenCommentNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> commentService.deleteComment(1L));

        assertEquals("Комментарий не найден", exception.getMessage());
        verify(commentRepository, never()).deleteById(anyLong());
    }

    @Test
    void getCommentCount_ShouldReturnCommentCount() {
        when(commentRepository.countByPost(testPost)).thenReturn(5);

        int count = commentService.getCommentCount(testPost);

        assertEquals(5, count);
        verify(commentRepository, times(1)).countByPost(testPost);
    }

    @Test
    void deleteCommentsByPostId_ShouldCallRepositoryMethod() {
        commentService.deleteCommentsByPostId(1L);

        verify(commentRepository, times(1)).deleteByPostId(1L);
    }
}