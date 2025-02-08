package ru.shintar.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shintar.blog.entity.Like;
import ru.shintar.blog.entity.Post;
import ru.shintar.blog.repository.LikeRepository;
import ru.shintar.blog.repository.PostRepository;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Пост не найден"));
        Like like = new Like();
        like.setPost(post);
        likeRepository.save(like);
    }

    public int getLikeCount(Post post) {
        return likeRepository.countByPost(post);
    }
    @Transactional
    public void deleteLikesByPostId(Long id) {
        likeRepository.deleteByPostId(id);
    }
}