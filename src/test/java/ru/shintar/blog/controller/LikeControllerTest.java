package ru.shintar.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shintar.blog.service.LikeService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LikeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
    }

    @Test
    void likePost_shouldRedirectToReturnUrl() throws Exception {
        Long postId = 1L;
        String returnUrl = "/custom-return";

        mockMvc.perform(post("/like/" + postId)
                        .param("returnUrl", returnUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(returnUrl));

        verify(likeService, times(1)).likePost(postId);
    }

    @Test
    void likePost_shouldRedirectToDefaultPage() throws Exception {
        Long postId = 1L;

        mockMvc.perform(post("/like/" + postId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?page=0&size=10"));

        verify(likeService, times(1)).likePost(postId);
    }

    @Test
    void likePost_shouldRedirectWithPageSizeAndTag() throws Exception {
        Long postId = 1L;
        int page = 2;
        int size = 5;
        String tag = "testTag";

        mockMvc.perform(post("/like/" + postId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("tag", tag))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?page=2&size=5&tag=testTag"));

        verify(likeService, times(1)).likePost(postId);
    }
}