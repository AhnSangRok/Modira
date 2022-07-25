package com.sparta.springweb.repository;

import com.sparta.springweb.model.Posts;
import com.sparta.springweb.model.Likes;
import com.sparta.springweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findLikesByPostsAndUser(Posts posts, User user);
    int countByPostsId(Long Id);

    @Modifying
    @Query(value = "INSERT INTO likes(posts_id, user_id) VALUES(:postsId, :userId)", nativeQuery = true)
    void likes(Long postsId, Long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE posts_id = postsId AND user_id = : userId", nativeQuery = true)
    void unlikes(Long postsId, Long userId);

}