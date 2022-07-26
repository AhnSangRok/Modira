package com.sparta.springweb.repository;

import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<postResponseDto> findByLocationName(String localName);
    List<Posts> findAllByOrderByModifiedAtDesc();
    List<Posts> findAllByOrderByCreatedAtDesc();

//    Posts findByUserName(String userName);
}