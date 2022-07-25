package com.sparta.springweb.repository;

import com.sparta.springweb.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
//    Likes findLikesByPostsAndUser(Posts posts, User user);
//    int countByPostsId(Long Id);
    List<Likes> findAllByPostId(Long PostId);
    Likes findOneByUserNameAndPostId(String userName, Long postId);

    void deleteByUserNameAndPostId(String userName, Long postId);

    boolean existsByPostIdAndUserName(Long postId, String userName);
//    @Modifying
//    @Query(value = "INSERT INTO likes(posts_id, user_id) VALUES(:postsId, :userId)", nativeQuery = true)
//    void likes(Long postsId, Long userId);
//
//    @Modifying
//    @Query(value = "DELETE FROM likes WHERE posts_id = postsId AND user_id = : userId", nativeQuery = true)
//    void unlikes(Long postsId, Long userId);
//

}