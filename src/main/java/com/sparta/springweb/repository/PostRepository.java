package com.sparta.springweb.repository;

import com.sparta.springweb.dto.postResponseDto;
import com.sparta.springweb.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Posts, Long> {
    Page<postResponseDto> findByLocationName(String localName, Pageable pageable);
}