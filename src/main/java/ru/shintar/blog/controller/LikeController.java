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

    private final LikeService likeService;

    @PostMapping("/like/{id}")
    public String likePost(@PathVariable Long id,
                           @RequestParam(required = false, defaultValue = "0") Integer page,
                           @RequestParam(required = false, defaultValue = "10") Integer size,
                           @RequestParam(required = false) String tag,
                           @RequestParam(required = false) String returnUrl) {
        likeService.likePost(id);

        if (returnUrl != null) {
            return "redirect:" + returnUrl;
        }

        return "redirect:/?page=" + page + "&size=" + size +
                (tag != null ? "&tag=" + tag : "");
    }
}
