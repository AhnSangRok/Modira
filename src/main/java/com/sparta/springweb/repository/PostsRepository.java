package com.sparta.springweb.repository;

import com.sparta.springweb.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findAllByLocationName(int locationName, Pageable pageable);
//    List<Posts> findAllByLocationName(int locationName);
    List<Posts> findAllByOrderByCreatedAtDesc();
//    List<Posts> findAllByOrderByModifiedAtDesc();
//    Posts findByUserName(String userName);
}