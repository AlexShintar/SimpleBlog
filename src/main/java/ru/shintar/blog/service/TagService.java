package ru.shintar.blog.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.entity.Tag;
import ru.shintar.blog.repository.TagRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void save(Post post) {
        String tagsString = post.getTags();
        if (StringUtils.isBlank(tagsString)) {
            return;
        }

        Arrays.stream(tagsString.split(","))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .distinct()
                .forEach(tag -> tagRepository.save(new Tag(tag, post)));
    }

    public String getTags(Post post) {

        return tagRepository.findAllByPost(post)
                .stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
    }
    @Transactional
    public void deleteTagsByPostId(Long id) {
        tagRepository.deleteByPostId(id);
    }
}