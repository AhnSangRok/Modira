package com.sparta.springweb.repository;

import com.sparta.springweb.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
//    Likes findLikesByPostsAndUser(Posts posts, User user);
//    int countByPostsId(Long Id);
//    List<Likes> findAllByPostId(Long PostId);
//    Likes findOneByUserNameAndPostId(String userName, Long postId);
//    void deleteByUserNameAndPostId(String userName, Long postId);
//    boolean existsByPostIdAndUserId(Long postId, String userName);
//    Likes findByPostIdAndUserId(Long postId, Long UserId);
//
//    Likes deleteByPostIdAndUserId(Long postId, Long UserId);
    boolean existsByPostIdAndUserName(Long postId, String userName);
    void deleteByPostIdAndUserName(Long postId, String userName);



}