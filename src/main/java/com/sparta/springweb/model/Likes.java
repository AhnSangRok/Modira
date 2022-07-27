package com.sparta.springweb.model;

import com.sparta.springweb.dto.LikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    public Likes(LikeDto likeDto){
        this.postId = likeDto.getPostId();
        this.userName = likeDto.getUserName();
    }

}