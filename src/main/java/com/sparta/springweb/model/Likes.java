package com.sparta.springweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

//    @JoinColumn(name = "posts_id")
//    @ManyToOne
//    private Posts posts;
//
//    @JoinColumn(name = "user_id")
//    @JsonIgnoreProperties({"postsList"})
//    @ManyToOne
//    private User user;
    @Column(nullable = false)
    private Long postId;
    @Column(nullable = false)
    private String userName;

    public Likes(LikeDto likeDto){
        this.postId = likeDto.getPostId();
        this.userName = likeDto.getUserName();
    }

}