package com.sparta.springweb.service;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.model.Likes;
import com.sparta.springweb.model.Posts;
import com.sparta.springweb.model.User;
import com.sparta.springweb.repository.LikesRepository;
import com.sparta.springweb.repository.PostsRepository;
import com.sparta.springweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public void likes(LikeDto likeDto) {
//        User user = userRepository.findOneByUsername(username);
        int ch = 0;
        Likes likes = new Likes(likeDto);
        List<Likes> likesList = likesRepository.findAllByPostId(likes.getPostId());
        Posts post = postsRepository.findByUserName(likes.getUserName());

        for (Likes check : likesList) {
            if (Objects.equals(check.getUserName(), likes.getUserName())){
                likesRepository.deleteById(check.getId());
                ch = 1;
                break;
            }
        }
        if (ch == 0){
            likesRepository.save(likes);
        }
//        postsRepository.save(post);
        post.updateLikesCount(likesList.size());
//        likesRepository.likes(contentsId,user.getId());
    }

    @Transactional
    public void unLikes(Long contentsId, String username) {
        User user = userRepository.findOneByUsername(username);
//        likesRepository.unlikes(contentsId,user.getId());
    }

    @Transactional
    public List<Likes> findLikes(Long postId) {
        List<Likes> likes = likesRepository.findAllByPostId(postId);
        return likes;
    }



//    boolean likecheck = likesRepository.existsByPostIdAndUserName(LikeDto.getPostId(), LikeDto.getUserName());
////    if(!likecheck){
//        likeCount += 1;
//        likesRepository.save(LikeDto.getPostId, LikeDto.getUserName);
//    } else{
//        likeCount -= 1;
//        likesRepository.deleteByPostIdAndUserId(likeDto.getPostId, likeDto.getUserId);
//    }

}