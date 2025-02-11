package ru.shintar.blog.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shintar.blog.model.Post;
import ru.shintar.blog.model.Tag;
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
                .forEach(tag -> tagRepository.save(new Tag(tag, post.getId())));
    }

    public String getTags(Long postId) {

        return tagRepository.findAllByPostId(postId)
                .stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
    }
    @Transactional
    public void deleteTagsByPostId(Long id) {
        tagRepository.deleteByPostId(id);
    }
}