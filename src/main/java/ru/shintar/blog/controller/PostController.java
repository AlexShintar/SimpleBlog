package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.service.PostService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    @GetMapping
    public String posts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "tag", required = false) String tag,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = service.getPosts(pageable, tag);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("page", postsPage);
        model.addAttribute("size", size);
        model.addAttribute("tag", tag);

        return "posts";
    }


    @GetMapping("/rnd")
    public String rnd() {
        service.rnd();
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
        post.setUpdatedAt(LocalDateTime.now());

        if (tags != null) {
            post.setTags(tags);
        }

        service.save(post);

        return "redirect:/";
    }

}


