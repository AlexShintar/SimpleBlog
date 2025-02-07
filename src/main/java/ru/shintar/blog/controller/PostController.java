package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.service.PostService;

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
        Page<Post> postsPage = service.getPosts(pageable);

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
}


