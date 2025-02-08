package ru.shintar.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.shintar.blog.service.CommentService;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Включаем мокито вручную
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void shouldRedirectToPostPageAfterAddingComment() throws Exception {
        Long postId = 1L;
        String content = "New comment";

        mockMvc.perform(post("/comment/add")
                        .param("postId", postId.toString())
                        .param("content", content))
                .andExpect(status().is3xxRedirection())  // Проверяем, что редирект произошел
                .andExpect(header().string("Location", "/post/" + postId));  // Проверяем правильность URL редиректа

        verify(commentService, times(1)).addComment(postId, content);
    }

    @Test
    void shouldRedirectToPostPageAfterEditingComment() throws Exception {
        Long commentId = 1L;
        String newContent = "Updated comment";
        Long postId = 2L;

        when(commentService.updateComment(commentId, newContent)).thenReturn(postId);

        mockMvc.perform(post("/comment/edit/{id}", commentId)
                        .param("content", newContent))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/" + postId));

        verify(commentService, times(1)).updateComment(commentId, newContent);
    }

    @Test
    void shouldRedirectToPostPageAfterDeletingComment() throws Exception {
        Long commentId = 1L;
        Long postId = 2L;

        when(commentService.deleteComment(commentId)).thenReturn(postId);

        mockMvc.perform(get("/comment/delete/{id}", commentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/" + postId));

        verify(commentService, times(1)).deleteComment(commentId);
    }
}