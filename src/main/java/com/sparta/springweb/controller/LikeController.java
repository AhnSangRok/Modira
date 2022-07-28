package com.sparta.springweb.controller;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikesService likesService;
    //좋아요
    @PostMapping("/api/likes/{postId}")
    public void likes(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null) {
            LikeDto likeDto = new LikeDto(postId, userDetails.getUsername());
            likesService.likes(likeDto);
        }
    }
//    @GetMapping("/api/likes/{postId}")
//    public List<Likes> getLikes(@PathVariable Long postId){
//        return likesService.findLikes(postId);
//    }

}
