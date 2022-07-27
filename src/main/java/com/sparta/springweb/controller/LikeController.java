package com.sparta.springweb.controller;

import com.sparta.springweb.dto.LikeDto;
import com.sparta.springweb.security.UserDetailsImpl;
import com.sparta.springweb.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class LikeController {
    private final LikesService likesService;

    @PostMapping("/api/likes/{contentsId}")
    public void likes(@PathVariable Long contentsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            LikeDto likeDto = new LikeDto(contentsId, userDetails.getUsername());
            likesService.likes(likeDto);
        }
    }
}
