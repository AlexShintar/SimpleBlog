package ru.shintar.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.service.CommentService;
import ru.shintar.blog.service.PostService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void posts_shouldReturnPostsPage() throws Exception {
        when(postService.getPosts(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/").param("tag", "java"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts", "page", "size", "tag"));
    }
    @Test
    void rnd_shouldRedirectToHome() throws Exception {
        mockMvc.perform(get("/rnd"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(postService, times(1)).rnd();
    }

    @Test
    void addPost_shouldRedirectToHome() throws Exception {
        mockMvc.perform(post("/addPost")
                        .param("title", "Test Title")
                        .param("content", "Test Content")
                        .param("imageUrl", "http://test.com/image.jpg")
                        .param("tags", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    void editPost_shouldRedirectToPostPage() throws Exception {
        Long postId = 1L;
        mockMvc.perform(post("/post/edit/" + postId)
                        .param("title", "Updated Title")
                        .param("content", "Updated Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/" + postId));

        verify(postService, times(1)).updatePost(eq(postId), any(Post.class));
    }

    @Test
    void deletePost_shouldRedirectToHome() throws Exception {
        Long postId = 1L;
        mockMvc.perform(post("/post/delete/" + postId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(postService, times(1)).deletePost(postId);
    }
}
