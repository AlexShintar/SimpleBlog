package ru.shintar.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.shintar.blog.service.CommentService;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestParam Long postId, @RequestParam String content) {
        commentService.addComment(postId, content);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/{id}/edit")
    public String updateComment(@PathVariable Long id, @RequestParam String content) {
        Long postId = commentService.updateComment(id, content);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        Long postId = commentService.deleteComment(id);
        return "redirect:/post/" + postId;
    }
}
