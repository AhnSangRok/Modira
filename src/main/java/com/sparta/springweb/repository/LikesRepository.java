package com.sparta.springweb.repository;

import com.sparta.springweb.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    List<Likes> findAllByPostId(Long PostId);
