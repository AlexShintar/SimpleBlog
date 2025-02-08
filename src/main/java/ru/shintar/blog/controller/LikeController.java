package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shintar.blog.service.LikeService;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeService service;
    @PostMapping("/like/{id}")
    public String likePost(@PathVariable Long id,
                           @RequestParam(required = false) Integer page,
                           @RequestParam(required = false) Integer size,
                           @RequestParam(required = false) String tag) {
        service.likePost(id);

        return "redirect:/?page=" + (page != null ? page : 0) +
                "&size=" + (size != null ? size : 10) +
                (tag != null ? "&tag=" + tag : "");
    }
}