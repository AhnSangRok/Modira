package com.sparta.springweb.dto;

import lombok.Getter;

@Getter
public class LikeDto {
    private Long postId;
    private String userName;

    public LikeDto(Long postId, String userName){
        this.postId = postId;
        this.userName = userName;
    }
}