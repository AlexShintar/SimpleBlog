package ru.shintar.blog.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.entity.Tag;
import ru.shintar.blog.repository.TagRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository repository;

    @Transactional
    public void save(Post post) {
        if (StringUtils.isBlank(post.getTags())) {
            return;
        }
        Arrays.stream(post.getTags().split(","))
                .forEach(t -> repository.save(new Tag(t, post)));
    }

    public String getTags(Post post) {
        var tags = repository.findAllByPost(post);
        String tagString = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
        return tagString;
    }
}