package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping
    public String posts(Model model) {
        List<Post> posts = service.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/rnd")
    public String fill(Model model) {
        service.rnd();
        List<Post> posts = service.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }
}


