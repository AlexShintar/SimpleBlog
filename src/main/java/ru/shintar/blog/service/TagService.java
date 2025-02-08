package ru.shintar.blog.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.entity.Tag;
import ru.shintar.blog.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
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
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .distinct()
                .forEach(tag -> repository.save(new Tag(tag, post)));
    }

    @Transactional
    public void update(Post post) {

        List<Tag> existingTags = repository.findAllByPost(post);
        if (!existingTags.isEmpty()) {
            repository.deleteAll(existingTags); // Удаляем только если есть что удалять
        }
        save(post);
    }


    public String getTags(Post post) {
        var tags = repository.findAllByPost(post);
        String tagString = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
        return tagString;
    }
}