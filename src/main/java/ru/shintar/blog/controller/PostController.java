package ru.shintar.blog.controller;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.shintar.blog.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    static Faker faker = new Faker();

    @GetMapping
    public String posts(Model model) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .id(faker.random().nextLong(100))
                    .title(faker.company().name())
                    .content(faker.address().fullAddress())
                    .imageUrl(faker.internet().url())
                    .updatedAt(LocalDateTime.now())
                    .build();
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "posts";
    }

}


