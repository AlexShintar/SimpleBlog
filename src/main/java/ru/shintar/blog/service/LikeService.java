package ru.shintar.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shintar.blog.model.Like;
import ru.shintar.blog.repository.LikeRepository;


@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public void likePost(Long postId) {
        Like like = new Like();
        like.setPostId(postId);
        likeRepository.save(like);
    }
    public int getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
    public void deleteLikesByPostId(Long postId) {
        likeRepository.deleteByPostId(postId);
    }
}