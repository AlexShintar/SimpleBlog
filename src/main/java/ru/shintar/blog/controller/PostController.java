package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shintar.blog.model.Comment;
import ru.shintar.blog.model.Post;
import ru.shintar.blog.service.CommentService;
import ru.shintar.blog.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public String posts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "tag", required = false) String tag,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postService.getPosts(pageable, tag);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("page", postsPage);
        model.addAttribute("size", size);
        model.addAttribute("tag", tag);

        return "posts";
    }

    @GetMapping("/rnd")
    public String rnd() {
        postService.rnd();
        return "redirect:/";
    }

    @PostMapping("/addPost")
    public String addPost(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String imageUrl,
                          @RequestParam(required = false) String tags) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setTags(tags);

        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);

        List<Comment> comments = commentService.getComments(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("currentUrl", "/post/" + id);
        return "post";
    }

    @PostMapping("/post/{id}/edit")
    public String editPost(@PathVariable Long id, @ModelAttribute Post postData) {
        postService.updatePost(id, postData);
        return "redirect:/post/" + id;
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }
}

